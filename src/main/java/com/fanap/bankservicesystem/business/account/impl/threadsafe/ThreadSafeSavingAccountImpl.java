package com.fanap.bankservicesystem.business.account.impl.threadsafe;

import com.fanap.bankservicesystem.business.account.SavingAccount;
import com.fanap.bankservicesystem.business.account.impl.CheckingAccountImpl;
import com.fanap.bankservicesystem.business.exception.ExceptionMessageCodes;
import com.fanap.bankservicesystem.business.exception.InsufficientFundsException;
import com.fanap.bankservicesystem.business.exception.InvalidTransactionException;
import com.fanap.bankservicesystem.business.util.InputValueUtil;

public final class ThreadSafeSavingAccountImpl extends ThreadSafeCheckingAccountImpl implements SavingAccount {

    /**
     * interestRate: is assumed as daily interest rate
     */
    private final double interestRate;
    private double minimumBalance;

    public ThreadSafeSavingAccountImpl(String accountNumber,
                                       String accountHolderName,
                                       Double balance,
                                       Double interestRate,
                                       Double minimumBalance) {
        super(accountNumber, accountHolderName, balance, 0d);
        this.interestRate = InputValueUtil.getNonNegativeValue(interestRate);
        this.minimumBalance = minimumBalance;
    }

    @Override
    public synchronized void withdraw(double amount) throws InsufficientFundsException, InvalidTransactionException {
        if (balance - amount >= minimumBalance)
            super.withdraw(amount);
        else
            throw new InvalidTransactionException(ExceptionMessageCodes.BSS_MINIMUM_BALANCE_LIMIT);
    }

    public synchronized void applyInterest(double averageBalance, int numberOfDays) {
        if (averageBalance > 0 && numberOfDays > 0)
            super.setBalance(balance + averageBalance * numberOfDays * interestRate);
    }

}
