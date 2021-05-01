package com.gvatreya.finmidbanking.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

/**
 * POJO for Transaction, backed by transactions table
 *
 */
@Entity
@Data
@NoArgsConstructor
public class Transaction {

    @Id
    private long id; // Primary Key

    private String transactionUuid;
    private double value;
    private long sourceAccountId;
    private long destAccountId;
    private Date time;
}
