package com.fanap.bankservicesystem.business.account.impl;


import com.fanap.bankservicesystem.business.account.BankAccount;
import com.fanap.bankservicesystem.business.exception.ExceptionMessageCodes;
import com.fanap.bankservicesystem.business.exception.InsufficientFundsException;
import com.fanap.bankservicesystem.business.exception.InvalidTransactionException;
import com.fanap.bankservicesystem.business.util.InputValueUtil;

import java.io.Serializable;
import java.lang.IllegalArgumentException;

public class BankAccountImpl implements BankAccount, Serializable {

    protected static final long serialVersionUID = 2L;
    private final String accountNumber;
    private final String accountHolderName;
    private double balance;

    public BankAccountImpl(String accountNumber, String accountHolderName, Double balance) {
        InputValueUtil.checkStringsHaveValue(new String[]{accountNumber, accountHolderName});
        this.accountNumber = accountNumber;
        this.accountHolderName = accountHolderName;
        this.balance = InputValueUtil.getNonNegativeValue(balance);
    }

    public void deposit(double amount) {
        if (amount < 0)
            throw new IllegalArgumentException(ExceptionMessageCodes.BSS_NEGATIVE_AMOUNT);

        balance += amount;
    }

    public void withdraw(double amount) throws InsufficientFundsException, InvalidTransactionException {
        if (amount < 0)
            throw new IllegalArgumentException(ExceptionMessageCodes.BSS_NEGATIVE_AMOUNT);
        if (amount > this.balance)
            throw new InsufficientFundsException(ExceptionMessageCodes.BSS_INSUFFICIENT_BALANCE);

        balance -= amount;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    protected void setBalance(double balance) {
        this.balance = balance;
    }

    protected double getBalance() {
        return balance;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

}
