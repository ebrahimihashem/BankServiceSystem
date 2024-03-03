package com.fanap.bankservicesystem.testcase;

import com.fanap.bankservicesystem.business.account.BankAccount;
import com.fanap.bankservicesystem.business.account.SavingAccount;
import com.fanap.bankservicesystem.business.account.impl.threadsafe.ThreadSafeCheckingAccountImpl;
import com.fanap.bankservicesystem.business.account.impl.threadsafe.ThreadSafeSavingAccountImpl;

public class Week2 {

    public static void testCase() {
        testThreadSafeCheckingAccount();
        testThreadSafeSavingAccount();
    }

    private static void testThreadSafeCheckingAccount() {
        BankAccount bankAccount = new ThreadSafeCheckingAccountImpl(
                "1", "a", 0d, 5000000d);

        // Create a thread for depositing
        Thread depositThread = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                bankAccount.deposit(3000);
                //System.out.println("Deposited 3000");
            }
        });

        // Create a thread for withdrawing
        Thread withdrawThread = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                bankAccount.withdraw(1000);
                //System.out.println("Withdrew 1000");
            }
        });

        // Start the threads
        depositThread.start();
        withdrawThread.start();

        // Wait for both threads to finish
        try {
            depositThread.join();
            withdrawThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //balance should be -1300
        PrintUtil.printAccountInfo(bankAccount);
    }

    private static void testThreadSafeSavingAccount() {
        SavingAccount bankAccount = new ThreadSafeSavingAccountImpl(
                "2", "b", 1000000d, 1d, 100d);

        // Create a thread for apply interest
        Thread applyInterestThread = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                bankAccount.applyInterest(3000, 1);
                //System.out.println("Deposited 3000");
            }
        });

        // Create a thread for withdrawing
        Thread withdrawThread = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                bankAccount.withdraw(1000);
                //System.out.println("Withdrew 1000");
            }
        });

        // Start the threads
        applyInterestThread.start();
        withdrawThread.start();

        // Wait for both threads to finish
        try {
            applyInterestThread.join();
            withdrawThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //balance should be 999000
        PrintUtil.printAccountInfo(bankAccount);
    }

}