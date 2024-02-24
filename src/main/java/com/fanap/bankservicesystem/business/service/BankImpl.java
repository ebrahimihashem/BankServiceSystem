package com.fanap.bankservicesystem.business.service;

import com.fanap.bankservicesystem.business.account.BankAccount;
import com.fanap.bankservicesystem.business.util.InputValueUtil;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class BankImpl implements Bank, Serializable {

    private static final long serialVersionUID = 1L;
    private static BankImpl instance;
    private Map<String, BankAccount> accountMap;

    private BankImpl() {
    }

    synchronized public static BankImpl getInstance() {
        if (instance == null) {
            instance = new BankImpl();
        }
        return instance;
    }

    @Override
    public void addAccount(BankAccount account) {
        if (accountMap == null)
            accountMap = new HashMap<>();
        if (account != null)
            accountMap.put(account.getAccountNumber(), account);
    }

    @Override
    public void removeAccount(String accountNumber) {
        if (accountMap != null && accountNumber != null)
            accountMap.remove(accountNumber);
    }

    @Override
    public BankAccount findAccount(String accountNumber) {
        if (accountMap != null && accountNumber != null)
            return accountMap.get(accountNumber);
        return null;
    }

    @Override
    public Map<String, BankAccount> listAccounts() {
        return accountMap;
    }
}
