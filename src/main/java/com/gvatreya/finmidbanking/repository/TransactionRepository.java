package com.gvatreya.finmidbanking.repository;

import com.gvatreya.finmidbanking.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    Optional<Transaction> findTransactionsByTransactionUuid(final String transactionUuid);

    /**
     * Fetch all transactions where the accountId passed was a participant.
     * @param accountId AccountId whose transactions are required.
     * @return {@code List<Transaction>} where accountId was a participant
     */
    @Query("SELECT t FROM Transaction t " +
            "WHERE (t.sourceAccountId = :accountId OR t.destAccountId = :accountId) " +
            "ORDER BY t.time DESC")
    List<Transaction> findTransactionsByAccountId(@NonNull @Param("accountId") final Long accountId);
}
