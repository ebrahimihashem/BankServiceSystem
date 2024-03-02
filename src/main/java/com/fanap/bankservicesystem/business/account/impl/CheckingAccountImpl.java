package com.fanap.bankservicesystem.business.account.impl;

import com.fanap.bankservicesystem.business.account.BankAccount;
import com.fanap.bankservicesystem.business.exception.ExceptionMessageCodes;
import com.fanap.bankservicesystem.business.exception.InsufficientFundsException;
import com.fanap.bankservicesystem.business.util.InputValueUtil;

public class CheckingAccountImpl extends BankAccountImpl implements BankAccount {

    private final double overDraftLimit;

    private static final double WITHDRAW_FEE = 2000;
    private static final double GET_BALANCE_FEE = 1000;
    private static final double DEPOSIT_FEE_RATE = 0.001;

    public CheckingAccountImpl(String accountNumber, String accountHolderName, Double balance, Double overDraftLimit) {
        super(accountNumber, accountHolderName, balance);
        this.overDraftLimit = InputValueUtil.getNonNegativeValue(overDraftLimit);
    }


    @Override
    public void deposit(double amount) {
        super.deposit(amount);
        deductFees(Operation.DEPOSIT, amount);
    }

    @Override
    public void withdraw(double amount) throws InsufficientFundsException {
        if (amount < 0)
            throw new IllegalArgumentException(ExceptionMessageCodes.BSS_NEGATIVE_AMOUNT);

        if (amount + WITHDRAW_FEE > super.getBalance() + overDraftLimit)
            throw new InsufficientFundsException(ExceptionMessageCodes.BSS_INSUFFICIENT_BALANCE_AND_OVER_DRAFT_LIMIT);
        else
            setBalance(super.getBalance() - amount);

        deductFees(Operation.WITHDRAW, null);
    }

    @Override
    public double getBalance() {
        if (super.getBalance() + overDraftLimit > GET_BALANCE_FEE) {
            deductFees(Operation.GET_BALANCE, null);
            return super.getBalance();
        }
        throw new InsufficientFundsException(ExceptionMessageCodes.BSS_INSUFFICIENT_BALANCE_AND_OVER_DRAFT_LIMIT);
    }

    private void deductFees(Operation operation, Double depositAmount) {
        if (Operation.WITHDRAW.equals(operation))
            setBalance(super.getBalance() - WITHDRAW_FEE);
        if (Operation.GET_BALANCE.equals(operation))
            setBalance(super.getBalance() - GET_BALANCE_FEE);
        if (Operation.DEPOSIT.equals(operation))
            setBalance(super.getBalance() - depositAmount * DEPOSIT_FEE_RATE);
    }

}
