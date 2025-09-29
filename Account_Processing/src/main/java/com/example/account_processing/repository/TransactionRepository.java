package com.example.account_processing.repository;

import com.example.account_processing.entite.transaction.Transaction;
import org.springframework.data.repository.CrudRepository;

public interface TransactionRepository extends CrudRepository<Transaction, String> {

}
