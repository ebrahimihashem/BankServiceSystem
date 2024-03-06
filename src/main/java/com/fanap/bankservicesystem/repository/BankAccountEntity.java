package com.fanap.bankservicesystem.repository;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class BankAccountEntity {

    @Id
    @GeneratedValue
    private int id;
    @Column(name = "Account_Number", unique = true, nullable = false)
    private String accountNumber;
    @Column(name = "Account_Holder_Name", nullable = false)
    private String accountHolderName;
    @Column(name = "Balanace_Value", nullable = false)
    private Double balance;

    public int getId() {
        return id;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public void setAccountHolderName(String accountHolderName) {
        this.accountHolderName = accountHolderName;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }
}
