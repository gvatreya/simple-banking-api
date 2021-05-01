package com.gvatreya.finmidbanking.model.dto;

import lombok.*;

import java.util.Date;

@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
public class TransactionDto {

    private String transactionUuid;
    private Double value;
    private Long sourceAccountId;
    private Long destAccountId;
    private Date time;
}
