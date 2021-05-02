package com.gvatreya.finmidbanking.service;

import com.gvatreya.finmidbanking.model.dto.TransactionDto;
import org.springframework.lang.NonNull;

import java.util.Collection;

public interface TransactionService {

    TransactionDto getTransactionDetails(@NonNull final String transactionUuid);

    /**
     * List all transactions where the accountId was a participant,
     * listing the latest first
     * @param accountId Id of account whose transactions one needs
     * @return {@code List<Transaction>} where accountId was a participant
     */
    Collection<TransactionDto> getAllTransactionsForAccount(@NonNull final Long accountId);

    TransactionDto createTransaction(@NonNull final TransactionDto transactionDto);

    /**
     * Helper method to check if the given accountId has sufficient balance to
     * proceed with the transaction. This effectively checks if the account will
     * still have the minimum balance after the transaction is complete.
     * @param accountId account where the amount will be debited from.
     * @param transactionAmount value of the amount to be debited.
     * @return {@code True} if the account will have the minimum balance
     *         after the transaction is complete. {@code False}, otherwise.
     */
    boolean hasSufficientBalance(@NonNull final Long accountId, @NonNull final Double transactionAmount);
}
