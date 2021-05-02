package com.gvatreya.finmidbanking.model.dto;

import com.gvatreya.finmidbanking.exceptions.ApplicationException;
import com.gvatreya.finmidbanking.model.Transaction;
import com.gvatreya.finmidbanking.utils.ValidationResponse;
import lombok.*;
import org.springframework.lang.NonNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
public class TransactionDto {

    private String transactionUuid;
    private Double value;
    private Long sourceAccountId;
    private Long destAccountId;
    private Date time;

    public static TransactionDto fromModel(@NonNull final Transaction transaction) {
        return new TransactionDtoBuilder()
                .transactionUuid(transaction.getTransactionUuid())
                .value(transaction.getValue())
                .sourceAccountId(transaction.getSourceAccountId())
                .destAccountId(transaction.getDestAccountId())
                .time(transaction.getTime())
                .build();
    }

    /**
     * Helper method that validates the TransactionDto object
     * @return {@link ValidationResponse}
     */
    public ValidationResponse validateForCreate() {
        final ValidationResponse response = new ValidationResponse();
        // Set true by default
        response.setValid(true);
        final List<String> problems = new ArrayList<>();

        if(null == this.sourceAccountId) {
            response.setValid(false);
            problems.add("sourceAccountId is null");
        }
        if(null != this.sourceAccountId && this.sourceAccountId < 0) {
            response.setValid(false);
            problems.add("sourceAccountId is negative");
        }
        if(null == this.destAccountId) {
            response.setValid(false);
            problems.add("destAccountId is null");
        }
        if(null != this.destAccountId && this.destAccountId < 0) {
            response.setValid(false);
            problems.add("destAccountId is negative");
        }
        if(null == this.value) {
            response.setValid(false);
            problems.add("value is null");
        }
        if(null != this.value && this.value < 0) {
            response.setValid(false);
            problems.add("value is negative");
        }
        if(null != this.sourceAccountId && this.sourceAccountId.equals(this.destAccountId)) {
            response.setValid(false);
            problems.add("Source and destination account ids are same and must be different");
        }
        response.setProblems(problems);
        return response;
    }
}
