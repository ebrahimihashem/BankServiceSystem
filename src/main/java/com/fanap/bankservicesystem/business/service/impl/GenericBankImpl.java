package com.fanap.bankservicesystem.business.service.impl;

import com.fanap.bankservicesystem.business.account.BankAccount;
import com.fanap.bankservicesystem.business.service.GenericBank;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class GenericBankImpl<T extends BankAccount> implements GenericBank<T>, Serializable {

    private static final long serialVersionUID = 1L;
    private static GenericBankImpl instance;
    private Map<String, T> accountMap;

    private GenericBankImpl() {
    }

    synchronized public static <T extends BankAccount> GenericBankImpl<T> getInstance() {
        if (instance == null) {
            instance = new GenericBankImpl<T>();
        }
        return instance;
    }

    synchronized public static <T extends BankAccount> void setInstance(GenericBankImpl<T> newInstance) {
        if (newInstance != null)
            instance = newInstance;
    }

    @Override
    public void addAccount(T account) {
        if (accountMap == null) {
            accountMap = new HashMap<>();
        }
        if (account != null)
            accountMap.put(account.getAccountNumber(), account);
    }

    @Override
    public void removeAccount(String accountNumber) {
        if (accountMap != null && accountNumber != null)
            accountMap.remove(accountNumber);
    }

    @Override
    public T findAccount(String accountNumber) {
        if (accountMap != null && accountNumber != null)
            return accountMap.get(accountNumber);
        return null;
    }

    @Override
    public Map<String, T> listAccounts() {
        return accountMap;
    }

}
