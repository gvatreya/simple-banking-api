package com.gvatreya.finmidbanking.repository;

import com.gvatreya.finmidbanking.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    @Query("UPDATE Account a " +
            "SET a.balance = :newBalance, a.updated = CURRENT_TIMESTAMP() " +
            "WHERE a.accountId = :accountId ")
    @Modifying
    int updateBalance(@Param("accountId") Long accountId, @Param("newBalance") Double newBalance);
}
