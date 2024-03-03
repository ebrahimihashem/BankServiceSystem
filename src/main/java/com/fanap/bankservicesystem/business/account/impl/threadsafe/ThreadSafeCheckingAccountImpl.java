package com.fanap.bankservicesystem.business.account.impl.threadsafe;

import com.fanap.bankservicesystem.business.account.BankAccount;
import com.fanap.bankservicesystem.business.account.impl.CheckingAccountImpl;
import com.fanap.bankservicesystem.business.exception.ExceptionMessageCodes;
import com.fanap.bankservicesystem.business.exception.InsufficientFundsException;

import java.util.concurrent.locks.ReentrantLock;

public class ThreadSafeCheckingAccountImpl extends CheckingAccountImpl implements BankAccount {

    private final Object lock = new Object();
    private final ReentrantLock reentrantLock = new ReentrantLock();

    public ThreadSafeCheckingAccountImpl(String accountNumber, String accountHolderName, Double balance, Double overDraftLimit) {
        super(accountNumber, accountHolderName, balance, overDraftLimit);
    }


    @Override
    public void deposit(double amount) {
        if (amount < 0)
            throw new IllegalArgumentException(ExceptionMessageCodes.BSS_NEGATIVE_AMOUNT);

        synchronized (lock) {
            super.setBalance(balance + amount);
            deductFees(Operation.DEPOSIT, amount);
        }
    }

    @Override
    public void withdraw(double amount) throws InsufficientFundsException {
        if (amount < 0)
            throw new IllegalArgumentException(ExceptionMessageCodes.BSS_NEGATIVE_AMOUNT);

        synchronized (lock) {
            if (amount + WITHDRAW_FEE > balance + overDraftLimit)
                throw new InsufficientFundsException(ExceptionMessageCodes.BSS_INSUFFICIENT_BALANCE_AND_OVER_DRAFT_LIMIT);
            else
                setBalance(balance - amount);
            deductFees(Operation.WITHDRAW, null);
        }

    }

    @Override
    public double getBalance() {
        if (balance + overDraftLimit > GET_BALANCE_FEE) {
            deductFees(Operation.GET_BALANCE, null);
            return balance;
        }
        throw new InsufficientFundsException(ExceptionMessageCodes.BSS_INSUFFICIENT_BALANCE_AND_OVER_DRAFT_LIMIT);
    }

    @Override
    protected void deductFees(Operation operation, Double depositAmount) {
        reentrantLock.lock();
        try {
            if (Operation.WITHDRAW.equals(operation))
                setBalance(balance - WITHDRAW_FEE);
            if (Operation.GET_BALANCE.equals(operation))
                setBalance(balance - GET_BALANCE_FEE);
            if (Operation.DEPOSIT.equals(operation))
                setBalance(balance - depositAmount * DEPOSIT_FEE_RATE);
        } finally {
            reentrantLock.unlock();
        }
    }

}
