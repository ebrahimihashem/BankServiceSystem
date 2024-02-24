package com.fanap.bankservicesystem.business.service;

import com.fanap.bankservicesystem.business.account.BankAccount;

import java.util.Map;

public interface Bank {

   void addAccount(BankAccount account);

   void removeAccount(String accountNumber);

   BankAccount findAccount(String accountNumber);

   Map<String,BankAccount> listAccounts();

}
