package com.fanap.bankservicesystem.business.util;

import com.fanap.bankservicesystem.business.account.BankAccount;
import com.fanap.bankservicesystem.business.service.Bank;
import com.fanap.bankservicesystem.business.service.BankImpl;

import java.io.*;
import java.util.Map;
import java.util.Set;

public class FileUtil {

    public static final String BANK_FILE_NAME = "BankData.ser";

    public static void writeBankDataToTheFile() throws IOException {
        FileOutputStream fileOut = new FileOutputStream(BANK_FILE_NAME);
        ObjectOutputStream out = new ObjectOutputStream(fileOut);
        out.writeObject(BankImpl.getInstance());
        out.close();
        fileOut.close();
    }

    public static void readBankDataFromTheFile() throws IOException, ClassNotFoundException {
        FileInputStream fileIn = new FileInputStream(BANK_FILE_NAME);
        ObjectInputStream in = new ObjectInputStream(fileIn);
        Bank loadedBank = (Bank) in.readObject();

        //Removing Current Bank Accounts Then Adding Loaded Accounts From The File
        Bank currentBank = BankImpl.getInstance();
        Set<Map.Entry<String, BankAccount>> currentAccounts = currentBank.listAccounts().entrySet();
        while (currentAccounts.iterator().hasNext())
            currentBank.removeAccount(currentAccounts.iterator().next().getKey());
        if (loadedBank.listAccounts() != null)
            loadedBank.listAccounts().forEach((k, v) -> currentBank.addAccount(v));

        in.close();
        fileIn.close();
    }


}
