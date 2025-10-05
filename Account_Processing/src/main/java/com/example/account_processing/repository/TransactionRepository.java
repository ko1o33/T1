package com.example.account_processing.repository;

import com.example.account_processing.entite.transaction.Transaction;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.Instant;

public interface TransactionRepository extends CrudRepository<Transaction, String> {

    @Query("SELECT COUNT(t) FROM Transaction t WHERE t.account.id = :accountId AND t.timestamp BETWEEN :start AND :end")
    Long countTransactionsByAccountAndTimeRange(@Param("accountId") Long accountId,
                                                @Param("timestamp") Instant start,
                                                @Param("end") Instant end);

}
