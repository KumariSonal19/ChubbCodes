package com.app.process;

import java.util.Comparator;
import com.app.dto.Account;

public class BalanceComparator implements Comparator<Account> {
    @Override
    public int compare(Account a1, Account a2) {
        return Double.compare(a1.getBalance(), a2.getBalance());
    }
}
