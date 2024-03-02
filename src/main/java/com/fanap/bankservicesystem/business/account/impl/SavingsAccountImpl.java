package com.fanap.bankservicesystem.business.account.impl;

import com.fanap.bankservicesystem.business.account.SavingAccount;
import com.fanap.bankservicesystem.business.exception.ExceptionMessageCodes;
import com.fanap.bankservicesystem.business.exception.InsufficientFundsException;
import com.fanap.bankservicesystem.business.exception.InvalidTransactionException;
import com.fanap.bankservicesystem.business.util.InputValueUtil;

public final class SavingsAccountImpl extends CheckingAccountImpl implements SavingAccount {

    /**
     * interestRate: is assumed as daily interest rate
     */
    private final double interestRate;
    private double minimumBalance;

    public SavingsAccountImpl(String accountNumber,
                              String accountHolderName,
                              Double balance,
                              Double interestRate,
                              Double minimumBalance) {
        super(accountNumber, accountHolderName, balance, 0d);
        this.interestRate = InputValueUtil.getNonNegativeValue(interestRate);
        this.minimumBalance = minimumBalance;
    }

    @Override
    public void withdraw(double amount) throws InsufficientFundsException, InvalidTransactionException {
        if (getBalance() - amount >= minimumBalance)
            super.withdraw(amount);
        else
            throw new InvalidTransactionException(ExceptionMessageCodes.BSS_MINIMUM_BALANCE_LIMIT);
    }

    public void applyInterest(double averageBalance, int numberOfDays) {
        if (averageBalance > 0 && numberOfDays > 0)
            super.deposit(averageBalance * numberOfDays * interestRate);
    }

}
