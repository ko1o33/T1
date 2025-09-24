package com.example.client_processing.entite.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long  id;

    @Column(nullable = false, length = 128)
    private String login;

    @Column(nullable = false, length = 128)
    private String password;

    @Column(nullable = false, length = 128)
    private String email;

}
