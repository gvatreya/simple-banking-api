package com.gvatreya.finmidbanking.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

/**
 * POJO for Transaction, backed by transactions table
 *
 */
@Entity
@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
public class Transaction {

    @Id
    private Long id; // Primary Key

    private String transactionUuid;
    private Double value;
    private Long sourceAccountId;
    private Long destAccountId;
    private Date time;
}
