package com.fanap.bankservicesystem.business.account;

import com.fanap.bankservicesystem.business.exception.InsufficientFundsException;
import com.fanap.bankservicesystem.business.exception.InvalidTransactionException;

public interface BankAccount {

    void deposit(double amount);

    void withdraw(double amount) throws InsufficientFundsException, InvalidTransactionException;

    String getAccountNumber();

}
