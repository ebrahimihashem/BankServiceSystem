package com.fanap.bankservicesystem.business.util;

import com.fanap.bankservicesystem.business.service.BankImpl;

import java.io.*;

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
        BankImpl loadedBank = (BankImpl) in.readObject();
        BankImpl.setInstance(loadedBank);
        in.close();
        fileIn.close();
    }

}
