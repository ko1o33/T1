package com.example.credit_processing.repository;

import com.example.credit_processing.entite.paymentRegistry.ProductRegistry;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRegistryRepository extends CrudRepository<ProductRegistry, String> {
    List<ProductRegistry> findByClientId(Long clientId);

}
