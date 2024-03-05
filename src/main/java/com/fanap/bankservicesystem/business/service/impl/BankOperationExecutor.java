package com.fanap.bankservicesystem.business.service.impl;

import com.fanap.bankservicesystem.business.account.BankAccount;
import com.fanap.bankservicesystem.business.account.SavingAccount;
import com.fanap.bankservicesystem.business.service.Bank;

import java.math.BigDecimal;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;

public class BankOperationExecutor {

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
                        ((SavingAccount) account).applyInterest(1000, 1));
        //((SavingAccount) account).applyInterest(1000, 1);
        executor.shutdown();
        while (!executor.isTerminated()) {
            //Wait Until Executing Ends
        }
    }

    //Lambda And Stream
    public static BigDecimal getSumOfHighValueBalances(double minimumBalance) {
        Bank bank = BankImpl.getInstance();
        if (bank == null || bank.listAccounts() == null || bank.listAccounts().isEmpty())
            return BigDecimal.ZERO;

        return bank.listAccounts().values().parallelStream()
                .filter(account -> account.getBalance() >= minimumBalance)
                .map(account -> BigDecimal.valueOf(account.getBalance()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public static void filterSavingAccountsAndApplyInterest(){
        Bank bank = BankImpl.getInstance();
        if (bank == null || bank.listAccounts() == null || bank.listAccounts().isEmpty())
            return;

        bank.listAccounts().values().parallelStream()
                .filter(account -> account instanceof SavingAccount)
                .forEach(account -> ((SavingAccount) account).applyInterest(2000d,1));
    }

}
