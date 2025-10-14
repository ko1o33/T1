package com.example.client_processing.controller;

import com.example.client_processing.aop.annotation.HttpIncomeRequestLog;
import com.example.client_processing.aop.annotation.HttpOutcomeRequestLog;
import com.example.client_processing.aop.annotation.LogDatasourceError;
import com.example.client_processing.aop.annotation.Metric;
import com.example.client_processing.dto.other.ProductRequest;
import com.example.client_processing.service.ProductService;
import com.example.client_processing.util.mapper.ProductMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
@Slf4j
public class ProductController {

    private final ProductService productService;
    private final ProductMapper productMapper;

    @PostMapping("/create")
    @LogDatasourceError
    @Metric
    @HttpIncomeRequestLog
    @HttpOutcomeRequestLog
    public ResponseEntity<?> createProduct(@Valid @RequestBody ProductRequest productRequest) {
        try {
            log.info("create Product by {}", productRequest);
            var productMap = productMapper.toProduct(productRequest);
            var product = productService.createProduct(productMap);
            return ResponseEntity.ok(product);
        } catch (Exception e) {
            log.info("An error occurred while creating the product {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/get")
    @LogDatasourceError
    @Metric
    @HttpIncomeRequestLog
    @HttpOutcomeRequestLog
    public ResponseEntity<?> getProducts(@RequestParam String productId) {
        try {
            log.info("get Products by productId {}", productId);
            var product = productService.getProductById(productId);
            return ResponseEntity.ok(product);
        } catch (Exception e) {
            log.info("An error occurred while find the product {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/patch")
    @LogDatasourceError
    @Metric
    @HttpIncomeRequestLog
    @HttpOutcomeRequestLog
    public ResponseEntity<?> patchProductsName(@RequestParam String name, @RequestParam String productId) {
        try {
            log.info("change name to Product: {}: ", productId);
            var product = productService.changeNameToProduct(name, productId);
            return ResponseEntity.ok(product);
        } catch (Exception e) {
            log.info("An error occurred while change name the product {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/delete")
    @LogDatasourceError
    @Metric
    @HttpIncomeRequestLog
    @HttpOutcomeRequestLog
    public ResponseEntity<?> deleteProduct(@RequestParam String productId) {
        try {
            log.info("delete Product by productId {}", productId);
            productService.deleteProductById(productId);
            return ResponseEntity.ok("Product deleted successfully");
        } catch (Exception e) {
            log.info("An error occurred while delete the product {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


}
