package com.example.account_processing.repository;

import com.example.account_processing.entite.account.Account;
import com.example.account_processing.entite.payment.Payment;
import com.example.account_processing.entite.payment.TypeList;
import com.example.account_processing.entite.transaction.Transaction;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface PaymentRepository extends CrudRepository<Payment, String> {


    @Query("SELECT COUNT(p) > 0 FROM Payment p " +
            "WHERE p.accountId = :account_id AND p.type = 'EXPIRED'")
    boolean existsByIsCreditAndAccountId(@Param("account_id") Long accountId);

    List<Payment> findByAccountIdAndTypeOrderByPaymentDate(Long accountId, TypeList type);

    @Modifying
    @Query("UPDATE Payment p SET p.type = ?1 WHERE p.id = ?2")
    void updatePayment(String type, Long Id);

    @Query("SELECT p FROM Payment p " +
            "WHERE p.accountId = :account_id AND p.type IN ('LOAN_PAYMENT', 'EXPIRED')")
    List<Payment> findByAccountIdForCredit(@Param("account_id") Long accountId);

    List<Payment> findByPaymentDate(LocalDate date);
}
