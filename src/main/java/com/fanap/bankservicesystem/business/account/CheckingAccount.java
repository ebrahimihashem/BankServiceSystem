package com.fanap.bankservicesystem.business.account;

import com.fanap.bankservicesystem.business.exception.ExceptionMessageCodes;
import com.fanap.bankservicesystem.business.exception.InsufficientFundsException;
import com.fanap.bankservicesystem.business.util.InputValueUtil;

public final class CheckingAccount extends BankAccount {

    private final double overDraftLimit;

    private static final double LOW_VALUE_TRANSACTION_FEE = 1000;
    private static final double HIGH_VALUE_TRANSACTION_FEE = 2000;
    private static final double LOW_VALUE_TRANSACTION_LIMIT = 1000000;

    public CheckingAccount(String accountNumber, String accountHolderName, Double balance, Double overDraftLimit) {
        super(accountNumber, accountHolderName, balance);
        this.overDraftLimit = InputValueUtil.getNonNegativeValue(overDraftLimit);
    }

    @Override
    public void withdraw(double amount) throws InsufficientFundsException {
        if (amount < 0)
            throw new IllegalArgumentException(ExceptionMessageCodes.BSS_NEGATIVE_AMOUNT);

        double fee = calculateFee(amount);

        if (amount + fee > getBalance() + overDraftLimit)
            throw new InsufficientFundsException(ExceptionMessageCodes.BSS_INSUFFICIENT_BALANCE_AND_OVER_DRAFT_LIMIT);
        else
            setBalance(getBalance() - amount);

        deductFees(fee);
    }

    private void deductFees(double fee) {
        setBalance(getBalance() - fee);
    }

    private double calculateFee(double amount) {
        if (amount < LOW_VALUE_TRANSACTION_LIMIT)
            return LOW_VALUE_TRANSACTION_FEE;
        else
            return HIGH_VALUE_TRANSACTION_FEE;
    }

}
