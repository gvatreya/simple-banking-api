package com.gvatreya.finmidbanking.model;

import lombok.*;
import org.springframework.lang.NonNull;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.util.Date;

/**
 * POJO for Transaction, backed by transactions table
 *
 */
@Entity
@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor @ToString
public class Transaction {

    @Id
    @GeneratedValue
    private Long id; // Primary Key

    @NonNull @Column(unique = true)
    private String transactionUuid;
    @NonNull
    private Double value;
    @NonNull
    private Long sourceAccountId;
    @NonNull
    private Long destAccountId;
    @NonNull @Builder.Default
    private Date time = new Date();

}
