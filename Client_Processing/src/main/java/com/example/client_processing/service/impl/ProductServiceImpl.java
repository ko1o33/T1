package com.example.client_processing.service.impl;

import com.example.client_processing.entite.product.Product;
import com.example.client_processing.exception.ProductException;
import com.example.client_processing.repository.ProductRepository;
import com.example.client_processing.service.ProductService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    @Transactional
    public Product createProduct(Product product) {
        log.info("save Product: {}", product);
        productRepository.save(product);
        return product;
    }

    @Override
    public Product getProductById(String id) {
        var optional = productRepository.findByProductId(id);
        if (optional.isEmpty()) {
            log.info("product not found {}", id);
            throw new ProductException("Product not found");
        }
        log.info("product found {}", optional.get());
        return optional.get();
    }

    @Override
    @Transactional
    public void deleteProductById(String id) {
        if (productRepository.findByProductId(id).isEmpty()) {
            log.info("product not found {}", id);
            throw new ProductException("Product not found");
        }
    }

    @Override
    public Product changeNameToProduct(String name, String product) {
        var oldProduct = productRepository.findByProductId(name);
        Product newProduct = oldProduct.get();
        newProduct.setName(name);
        log.info("Update name to Product: {}", newProduct);
        return newProduct;
    }
}
