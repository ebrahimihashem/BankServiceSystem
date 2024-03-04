package com.fanap.bankservicesystem.business.service.impl;

import com.fanap.bankservicesystem.business.account.BankAccount;
import com.fanap.bankservicesystem.business.account.SavingAccount;
import com.fanap.bankservicesystem.business.service.Bank;
import com.fanap.bankservicesystem.business.service.impl.BankImpl;

import java.math.BigDecimal;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;

public class BankOperationExecutor {

    private ExecutorService executor = Executors.newFixedThreadPool(16); // Adjust the pool size as needed

    //Multithreading
    public static BigDecimal calculateBankTotalBalance() {
        AtomicReference<BigDecimal> totalBalance = new AtomicReference<>(BigDecimal.ZERO);
        Bank bank = BankImpl.getInstance();
        if (bank == null || bank.listAccounts() == null || bank.listAccounts().isEmpty())
            return totalBalance.get();

        ExecutorService executor = Executors.newFixedThreadPool(16); // Adjust the pool size as needed

        for (BankAccount account : bank.listAccounts().values())
            executor.execute(() ->
                    totalBalance.accumulateAndGet(BigDecimal.valueOf(account.getBalance()), BigDecimal::add));

        executor.shutdown();
        while (!executor.isTerminated()) {
            //Wait Until Executing Ends
        }

        return totalBalance.get();
    }

    //Multithreading
    public static void applyInterestToAllAccounts() {
        Bank bank = BankImpl.getInstance();
        if (bank == null || bank.listAccounts() == null || bank.listAccounts().isEmpty())
            return;

        ExecutorService executor = Executors.newFixedThreadPool(16); // Adjust the pool size as needed

        for (BankAccount account : bank.listAccounts().values())
            if (account instanceof SavingAccount)
                              executor.execute(() ->
                  ((SavingAccount) account).applyInterest(1000,1));
                //((SavingAccount) account).applyInterest(1000, 1);
        executor.shutdown();
        while (!executor.isTerminated()) {
            //Wait Until Executing Ends
        }
    }

}
