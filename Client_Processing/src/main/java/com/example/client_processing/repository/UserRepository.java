package com.example.client_processing.repository;

import com.example.client_processing.entite.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
}
