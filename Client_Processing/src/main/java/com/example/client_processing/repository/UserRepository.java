package com.example.client_processing.repository;

import com.example.client_processing.entite.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByLogin(String login);

    boolean existsByEmail(String email);

    Optional<User> findByLoginAndPassword(String login, String password);

    Optional<User> findByLogin(String login);

}
