package com.example.account_processing.repository;

import com.example.account_processing.entite.account.Account;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AccountRepository extends CrudRepository<Account, String> {

    Account getById(Long id);

}
