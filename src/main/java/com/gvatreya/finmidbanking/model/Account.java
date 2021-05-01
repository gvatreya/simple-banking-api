package com.gvatreya.finmidbanking.model;

import lombok.*;
import org.springframework.lang.NonNull;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

/**
 * POJO for Account, backed by accounts table
 *
 */
@Entity
@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor @ToString
public class Account {

    @Id
    @GeneratedValue
    private Long id; // Primary Key

    @NonNull @Column(unique = true)
    private Long accountId;
    @NonNull
    private Double balance;

    // Book Keeping attributes
    private Date created;
    private Date updated;
    private Date deleted;

}
