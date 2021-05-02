package com.gvatreya.finmidbanking;

import com.gvatreya.finmidbanking.model.Account;
import com.gvatreya.finmidbanking.model.Transaction;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Helper class with static methods to create stubs.
 */
public class TestUtility {

    public static final Long ACCOUNT_ID = 1L;
    public static final Long ID = 1L;
    public static final Double BALANCE = 3000D;
    public static final Date CURRENT_TIME = new Date();
    public static final Long SRC_ACC_ID = 1L;
    public static final Long DEST_ACC_ID = 2L;
    public static final String UUID_STRING = UUID.randomUUID().toString();
    public static final Double VALUE = 3000D;

    public static Account getPreBuiltAccount(){
        return Account.builder()
                .accountId(ACCOUNT_ID)
                .balance(BALANCE)
                .created(CURRENT_TIME)
                .updated(CURRENT_TIME)
                .deleted(null)
                .build();
    }

    public static Transaction getPreBuiltTransaction(){
        return Transaction.builder()
                .id(ID)
                .sourceAccountId(SRC_ACC_ID)
                .destAccountId(DEST_ACC_ID)
                .value(VALUE)
                .transactionUuid(UUID_STRING)
                .time(CURRENT_TIME)
                .build();
    }

    public static List<Account> getAccountsWithoutIds(final int count) {
        final List<Account> accounts = new ArrayList<>(count);
        for(int i=0; i<count; i++) {
            accounts.add(
                    Account.builder()
                            .accountId(ACCOUNT_ID + i)
                            .balance(BALANCE + i)
                            .created(CURRENT_TIME)
                            .updated(CURRENT_TIME)
                            .deleted(null)
                            .build()
            );
        }
        return accounts;
    }

}
