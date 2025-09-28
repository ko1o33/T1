package com.example.client_processing.repository;

import com.example.client_processing.entite.user.BlackListUser;
import org.springframework.data.repository.CrudRepository;

public interface BlackListUserRepository extends CrudRepository<BlackListUser, Long> {

    boolean existsByLogin(String login);
}
