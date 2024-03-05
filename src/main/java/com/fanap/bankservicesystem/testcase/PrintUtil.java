package com.fanap.bankservicesystem.testcase;

import com.fanap.bankservicesystem.business.account.BankAccount;
import com.fanap.bankservicesystem.business.account.impl.BankAccountImpl;
import com.fanap.bankservicesystem.business.service.GenericBank;
import com.fanap.bankservicesystem.business.service.impl.BankImpl;

public class PrintUtil {

    public static void printAccountInfo(BankAccount bankAccount) {
        BankAccountImpl bankAccountImpl = (BankAccountImpl) bankAccount;
        System.out.println("AccountNumber:" + bankAccountImpl.getAccountNumber()
                + " HolderName:" + bankAccountImpl.getAccountHolderName()
                + " balance:" + bankAccountImpl.getBalance());
    }

    public static void printBankData() {
        System.out.println("Bank Accounts:");
        if (BankImpl.getInstance().listAccounts() != null)
            BankImpl.getInstance().listAccounts().forEach((k, v) -> printAccountInfo(v));
    }

    public static void printGenericBankData(GenericBank<? extends BankAccount> bank) {
        System.out.println("Generic Bank Accounts:");
        if (bank.listAccounts() != null)
            bank.listAccounts().forEach((k, v) -> printAccountInfo(v));
    }

}
