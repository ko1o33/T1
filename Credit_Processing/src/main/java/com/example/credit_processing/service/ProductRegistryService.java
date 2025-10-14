package com.example.credit_processing.service;

import com.example.credit_processing.dto.ProductRegistryRequest;

public interface ProductRegistryService {

    void createProduct(String json);

    Long getSumAmount(Long clientId);

    boolean checkClient(ProductRegistryRequest product);

    boolean checkExpired(ProductRegistryRequest product);


}
