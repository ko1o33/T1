package com.example.client_processing.repository;

import com.example.client_processing.entite.client.Client;
import com.example.client_processing.entite.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {

    @Query(value = "SELECT client_id from clients c where client_id like :prefix% order by client_id DESC limit 1"
            , nativeQuery = true)
    String findLastClientId(@Param("prefix") String prefix);

    Optional<Client> getByUserId(User user);

    Optional<Client> getByClientId(String clientId);

}
