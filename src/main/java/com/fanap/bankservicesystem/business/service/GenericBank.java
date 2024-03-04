package com.fanap.bankservicesystem.business.service;

import com.fanap.bankservicesystem.business.account.BankAccount;

import java.util.Map;

public interface GenericBank<T extends BankAccount> {

    void addAccount(T account);

    void removeAccount(String accountNumber);

    T findAccount(String accountNumber);

    Map<String, T> listAccounts();

}
