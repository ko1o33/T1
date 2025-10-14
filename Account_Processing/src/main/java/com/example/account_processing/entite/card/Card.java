package com.example.account_processing.entite.card;

import com.example.account_processing.entite.account.Account;
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
@Table(name = "cards")
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @Column(name = "card_id", nullable = false, unique = true, length = 16)
    private String cardId;

    @Column(name = "payment_system", nullable = false)
    private String paymentSystem;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private StatusList status;
}
