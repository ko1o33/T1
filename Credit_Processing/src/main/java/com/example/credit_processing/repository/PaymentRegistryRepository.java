package com.example.credit_processing.repository;

import com.example.credit_processing.entite.paymentRegistry.ProductRegistry;
import com.example.credit_processing.entite.productRegistry.PaymentRegistry;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PaymentRegistryRepository extends CrudRepository<PaymentRegistry, Long> {
    @Query("SELECT SUM(p.amount) FROM PaymentRegistry p WHERE p.productRegistry IN :productRegistry")
    Long findByProductRegistry(@Param("productRegistry")List<ProductRegistry> productRegistry);

    @Query("SELECT COUNT(p) > 0 FROM PaymentRegistry p WHERE p.productRegistry.id IN" +
            " :productIds AND p.expired = true")
    boolean existsExpired(@Param("productRegistry")List<ProductRegistry> productRegistry);


}
