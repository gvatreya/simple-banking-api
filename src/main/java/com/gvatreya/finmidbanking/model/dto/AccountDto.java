package com.gvatreya.finmidbanking.model.dto;

import com.gvatreya.finmidbanking.model.Account;
import lombok.Builder;
import lombok.Data;
import lombok.Generated;
import org.springframework.lang.NonNull;

/**
 * Data transfer object for the model Account
 *
 */
@Data
@Builder
public class AccountDto {

    private long accountId;
    private double balance;

    public static AccountDto fromModel(@NonNull final Account account) {
        return new AccountDtoBuilder()
                .accountId(account.getAccountId())
                .balance(account.getBalance())
                .build();
    }

}
