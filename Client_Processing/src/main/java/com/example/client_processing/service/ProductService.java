package com.example.client_processing.service;

import com.example.client_processing.entite.product.Product;

public interface ProductService {
    Product createProduct(Product product);

    Product getProductById(String id);

    void deleteProductById(String id);
}
