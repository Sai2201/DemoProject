package com.esproject.demo.util;

import com.esproject.demo.models.BankAccountDetails;

import java.io.IOException;
import java.util.List;

public class BankAccountTestUtil {
    public static List<BankAccountDetails> getBankAccountDetails() throws IOException {
        return FileUtil.readFromJsonFile("src/test/resources/bankAccounts.json", BankAccountDetails.class);
    }
}
