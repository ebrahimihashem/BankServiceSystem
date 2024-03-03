package com.fanap.bankservicesystem.testcase;

import com.fanap.bankservicesystem.business.account.BankAccount;
import com.fanap.bankservicesystem.business.account.SavingAccount;
import com.fanap.bankservicesystem.business.account.impl.BankAccountImpl;
import com.fanap.bankservicesystem.business.account.impl.CheckingAccountImpl;
import com.fanap.bankservicesystem.business.account.impl.SavingAccountImpl;
import com.fanap.bankservicesystem.business.exception.InsufficientFundsException;
import com.fanap.bankservicesystem.business.service.BankImpl;
import com.fanap.bankservicesystem.business.util.FileUtil;

import java.io.IOException;

public class Week1 {

    public static void testCase() {
        testBankAccountAndAddToBank();
        testCheckingAccountAndAddToBank();
        testSavingAccountAndAddToBank();
        testFileUtil();
    }

    private static void testBankAccountAndAddToBank() {
        BankAccount bankAccount = new BankAccountImpl("001", "Ali", 1000d);
        testAccount(bankAccount);
        BankImpl.getInstance().addAccount(bankAccount);
    }


    private static void testCheckingAccountAndAddToBank() {
        BankAccount checkingAccount = new CheckingAccountImpl("002", "Bahram", 1000d, 5000d);
        testAccount(checkingAccount);
        BankImpl.getInstance().addAccount(checkingAccount);
    }

    private static void testSavingAccountAndAddToBank() {
        SavingAccount savingsAccount = new SavingAccountImpl(
                "003", "Sima", 6000d, 0.01, 1000d);
        testAccount(savingsAccount);
        savingsAccount.applyInterest(1000, 100);
        BankImpl.getInstance().addAccount(savingsAccount);
    }

    private static void testAccount(BankAccount bankAccount) {
        bankAccount.deposit(1000d);
        PrintUtil.printAccountInfo(bankAccount);
        bankAccount.withdraw(1000d);
        PrintUtil.printAccountInfo(bankAccount);
        try {
            bankAccount.withdraw(2000d);
        } catch (InsufficientFundsException e) {
            System.out.print("Trying to withdraw 2000: ");
            System.out.println(e.getMessage());
        }
    }

    private static void testFileUtil() {
        try {
            FileUtil.writeBankDataToTheFile();
            System.out.println("Current Bank Data:");
            PrintUtil.printBankData();
            System.out.println("Loading Bank Data:");
            FileUtil.readBankDataFromTheFile();
            PrintUtil.printBankData();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
