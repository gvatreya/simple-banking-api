package com.gvatreya.finmidbanking.model.dto;

import com.gvatreya.finmidbanking.model.Account;
import com.gvatreya.finmidbanking.utils.ValidationResponse;
import lombok.*;
import org.springframework.lang.NonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Data transfer object for the model Account
 *
 */
@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
public class AccountDto {

    private Long accountId;
    private Double balance;

    public static AccountDto fromModel(@NonNull final Account account) {
        return new AccountDtoBuilder()
                .accountId(account.getAccountId())
                .balance(account.getBalance())
                .build();
    }

    /**
     * Helper method that validates the AccountDto object
     * @return {@link ValidationResponse}
     */
    public ValidationResponse validate() {
        final ValidationResponse response = new ValidationResponse();
        // Set true by default
        response.setValid(true);
        final List<String> problems = new ArrayList<>();

        if(null == this.accountId) {
            response.setValid(false);
            problems.add("accountId is null");
        }
        if(null == this.balance) {
            response.setValid(false);
            problems.add("balance is null");
        }
        if(null != this.balance && this.balance < 0) {
            response.setValid(false);
            problems.add("balance is negative");
        }
        response.setProblems(problems);
        return response;
    }

}
