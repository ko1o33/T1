package com.example.account_processing.entite.account;

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
@Table(name = "accounts")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "client_id", nullable = false)
    private Long clientId;

    @Column(name = "product_id", nullable = false)
    private Long productId;

    @Column(name = "balance", nullable = false)
    private Long balance;

    @Column(name = "interest_rate", nullable = false)
    private String interestRate;

    @Column(name = "is_recalc", nullable = false)
    private boolean isRecalc;

    @Column(name = "card_exist", nullable = false)
    private boolean cardExist;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private StatusList status;
}
