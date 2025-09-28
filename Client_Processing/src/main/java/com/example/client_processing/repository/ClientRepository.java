package com.example.client_processing.repository;

import com.example.client_processing.entite.client.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Integer> {

}
