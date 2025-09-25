package com.example.account_processing.entite.payment;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "payments")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "account_id", nullable = false)
    private Long accountId;

    @Column(name = "amount", nullable = false)
    private Long amount;

    @Column(name = "payment_Date", nullable = false)
    private LocalDate paymentDate;

    @Column(name = "is_credit", nullable = false)
    private boolean isCredit;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private TypeList type;
}
