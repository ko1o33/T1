package com.example.credit_processing.repository;

import com.example.credit_processing.entite.paymentRegistry.ProductRegistry;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductRegistryRepository extends CrudRepository<ProductRegistry, String> {
    List<ProductRegistry> findByClientId(Long clientId);

}
