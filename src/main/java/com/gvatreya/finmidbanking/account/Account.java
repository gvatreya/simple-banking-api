package com.gvatreya.finmidbanking.account;

import lombok.Data;

/**
 * POJO for Account, backed by accounts table
 *
 */
@Data
public class Account {

    private long accountId;
    private double balance;

}
