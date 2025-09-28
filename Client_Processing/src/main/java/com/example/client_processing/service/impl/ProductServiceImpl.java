package com.example.client_processing.service.impl;

import com.example.client_processing.entite.product.Product;
import com.example.client_processing.exception.ProductException;
import com.example.client_processing.repository.ProductRepository;
import com.example.client_processing.service.ProductService;
import com.example.client_processing.util.mapper.ProductMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    @Transactional
    public Product createProduct(Product product) {
        productRepository.save(product);
        return product;
    }

    @Override
    public Product getProductById(String id) {
        var optional = productRepository.findByProductId(id);
        if (optional.isEmpty()){
            throw new ProductException("Product not found");
        }
        return optional.get();
    }

    @Override
    @Transactional
    public void deleteProductById(String id) {
        if(productRepository.findByProductId(id).isEmpty()){
            throw new ProductException("Product not found");
        }
    }
}
