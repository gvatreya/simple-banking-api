package com.gvatreya.finmidbanking.repository;

import com.gvatreya.finmidbanking.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    @Query("UPDATE Account a " +
            "SET a.balance = :newBalance, a.updated = CURRENT_TIMESTAMP() " +
            "WHERE a.accountId = :accountId ")
    @Modifying(clearAutomatically = true)
    int updateBalance(@Param("accountId") Long accountId, @Param("newBalance") Double newBalance);

    @Query("UPDATE Account a " +
            "SET a.balance = (a.balance - :amountToBeDebited), a.updated = CURRENT_TIMESTAMP() " +
            "WHERE a.accountId = :accountId ")
    @Modifying(clearAutomatically = true)
    int debitAccountBalance(@Param("accountId") Long accountId, @Param("amountToBeDebited") Double amountToBeDebited);

    @Query("UPDATE Account a " +
            "SET a.balance = (a.balance + :amountToBeCredited), a.updated = CURRENT_TIMESTAMP() " +
            "WHERE a.accountId = :accountId ")
    @Modifying(clearAutomatically = true)
    int creditAccountBalance(@Param("accountId") Long accountId, @Param("amountToBeCredited") Double amountToBeCredited);

    Optional<Account> findAccountByAccountId(@NonNull final Long accountId);

    boolean existsAccountByAccountId(@NonNull final Long accountId);
}
