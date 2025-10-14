package com.example.account_processing.repository;

import com.example.account_processing.entite.account.Account;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.Instant;
import java.util.Optional;

public interface AccountRepository extends CrudRepository<Account, String> {

    Account getById(Long id);

    @Modifying
    @Query("UPDATE Account a SET a.status = ?1 WHERE a.id = ?2")
    void blockAccount(String status, Long accountId);


}
