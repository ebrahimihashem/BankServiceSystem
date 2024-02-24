package com.fanap.bankservicesystem.business.account;

public interface CheckingAccount extends BankAccount {

    void deductFees(double fee);

}
