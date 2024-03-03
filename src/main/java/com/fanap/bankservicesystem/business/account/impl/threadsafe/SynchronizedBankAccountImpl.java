package com.fanap.bankservicesystem.business.account.impl.threadsafe;


import com.fanap.bankservicesystem.business.account.BankAccount;
import com.fanap.bankservicesystem.business.account.impl.BankAccountImpl;
import com.fanap.bankservicesystem.business.exception.ExceptionMessageCodes;
import com.fanap.bankservicesystem.business.exception.InsufficientFundsException;

import java.io.Serializable;

public class SynchronizedBankAccountImpl extends BankAccountImpl implements BankAccount, Serializable {

    public SynchronizedBankAccountImpl(String accountNumber, String accountHolderName, Double balance) {
        super(accountNumber, accountHolderName, balance);
    }

    @Override
    public void deposit(double amount) {
        if (amount < 0)
            throw new IllegalArgumentException(ExceptionMessageCodes.BSS_NEGATIVE_AMOUNT);
        try {
            balanceOperationHandler(Operation.DEPOSIT, amount);
        } catch (InsufficientFundsException ignored) {
        }
    }

    @Override
    public void withdraw(double amount) throws InsufficientFundsException {
        if (amount < 0)
            throw new IllegalArgumentException(ExceptionMessageCodes.BSS_NEGATIVE_AMOUNT);
        balanceOperationHandler(Operation.WITHDRAW, amount);
    }

    @Override
    public double getBalance() {
        Double currentBalance = null;
        try {
            currentBalance = balanceOperationHandler(Operation.GET_BALANCE, null);
        } catch (InsufficientFundsException ignored) {
        }
        if (currentBalance == null)
            return 0;
        return currentBalance;
    }

    private synchronized Double balanceOperationHandler(Operation operation, Double amount) throws InsufficientFundsException {
        if (Operation.GET_BALANCE.equals(operation))
            return super.getBalance();
        if (Operation.DEPOSIT.equals(operation))
            setBalance(super.getBalance() + amount);
        if (Operation.WITHDRAW.equals(operation)) {
            if (amount > super.getBalance())
                throw new InsufficientFundsException(ExceptionMessageCodes.BSS_INSUFFICIENT_BALANCE);
            setBalance(super.getBalance() - amount);
        }
        return null;
    }

}
