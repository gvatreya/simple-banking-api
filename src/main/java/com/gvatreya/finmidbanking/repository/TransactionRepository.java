package com.gvatreya.finmidbanking.repository;

import com.gvatreya.finmidbanking.model.Transaction;
import org.springframework.data.repository.CrudRepository;

public interface TransactionRepository extends CrudRepository<Transaction, Long> {
}
