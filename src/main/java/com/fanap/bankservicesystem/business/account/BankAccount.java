package com.fanap.bankservicesystem.business.account;

public interface BankAccount {

    void deposit(double amount);

    void withdraw(double amount);

    double getBalance();

    String getAccountNumber();

    String getAccountHolderName();

}
