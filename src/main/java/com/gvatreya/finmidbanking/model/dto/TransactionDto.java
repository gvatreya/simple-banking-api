package com.gvatreya.finmidbanking.model.dto;

import lombok.Data;

import java.util.Date;

@Data
public class TransactionDto {

    private String transactionUuid;
    private double value;
    private long sourceAccountId;
    private long destAccountId;
    private Date time;
}
