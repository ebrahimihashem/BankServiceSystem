package com.fanap.bankservicesystem.business.account;

public interface SavingAccount extends BankAccount{

    void applyInterest(double averageBalance, int numberOfDays);

}
