package com.fanap.bankservicesystem.business.account.impl.threadsafe;


import com.fanap.bankservicesystem.business.account.BankAccount;
import com.fanap.bankservicesystem.business.exception.ExceptionMessageCodes;
import com.fanap.bankservicesystem.business.exception.InsufficientFundsException;
import com.fanap.bankservicesystem.business.util.InputValueUtil;

import java.io.Serializable;
import java.util.concurrent.locks.ReentrantLock;

public class LockableBankAccountImpl implements BankAccount, Serializable {

    private final ReentrantLock accountLock = new ReentrantLock();
    protected static final long serialVersionUID = 3L;
    private final String accountNumber;
    private final String accountHolderName;
    private double balance;

    public LockableBankAccountImpl(String accountNumber, String accountHolderName, Double balance) {
        InputValueUtil.checkStringsHaveValue(new String[]{accountNumber, accountHolderName});
        this.accountNumber = accountNumber;
        this.accountHolderName = accountHolderName;
        this.balance = InputValueUtil.getNonNegativeValue(balance);
    }

    public void deposit(double amount) {
        if (amount < 0)
            throw new IllegalArgumentException(ExceptionMessageCodes.BSS_NEGATIVE_AMOUNT);

        accountLock.lock();
        try {
            balance += amount;
        } finally {
            accountLock.unlock();
        }
    }

    public void withdraw(double amount) throws InsufficientFundsException {
        if (amount < 0)
            throw new IllegalArgumentException(ExceptionMessageCodes.BSS_NEGATIVE_AMOUNT);

        accountLock.lock();
        try {
            if (amount > this.balance)
                throw new InsufficientFundsException(ExceptionMessageCodes.BSS_INSUFFICIENT_BALANCE);
            balance -= amount;
        } finally {
            accountLock.unlock();
        }
    }

    public double getBalance() {
        accountLock.lock();
        try {
            return balance;
        } finally {
            accountLock.unlock();
        }
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

}
