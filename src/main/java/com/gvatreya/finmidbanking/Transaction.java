package com.gvatreya.finmidbanking;

import lombok.Data;

@Data
public class Transaction {

    private String transactionUuid;
    private double value;
    private long sourceAccountId;
    private long destAccountId;
}
