package com.fanap.bankservicesystem.business.service.impl;

import com.fanap.bankservicesystem.business.account.BankAccount;
import com.fanap.bankservicesystem.business.service.GenericBank;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class GenericBankImpl<T extends BankAccount> implements GenericBank<T>, Serializable {

    private static final long serialVersionUID = 1L;
    private Map<String, T> accountMap;

    public GenericBankImpl() {
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
