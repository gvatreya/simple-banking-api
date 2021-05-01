package com.gvatreya.finmidbanking.model;

import com.gvatreya.finmidbanking.model.dto.AccountDto;
import lombok.*;
import org.springframework.lang.NonNull;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

/**
 * POJO for Account, backed by accounts table
 *
 */
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Account {

    @Id
    @GeneratedValue
    private long id; // Primary Key

    @NonNull
    private long accountId;
    @NonNull
    @Notn
    private double balance;

    // Book Keeping attributes
    private Date created;
    private Date updated;
    private Date deleted;

}
