package com.example.credit_processing.entite.productRegistry;

import com.example.credit_processing.entite.paymentRegistry.ProductRegistry;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "payment_registry")
public class PaymentRegistry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private ProductRegistry productRegistry;

    @Column(name = "paymend_date", nullable = false)
    private LocalDate paymendDate;

    @Column(name = "amount", nullable = false)
    private Long amount;

    @Column(name = "interest_rateAmount", nullable = false)
    private float interestRateAmount;

    @Column(name = "debt_amount", nullable = false)
    private Long debtAmount;

    @Column(name = "expired", nullable = false)
    private boolean expired;

    @Column(name = "payment_expirationDate", nullable = false)
    private LocalDate paymentExpirationDate;
}
