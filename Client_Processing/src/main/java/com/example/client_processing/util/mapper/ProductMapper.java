package com.example.client_processing.util.mapper;

import com.example.client_processing.dto.ProductRequest;
import com.example.client_processing.entite.product.KeyList;
import com.example.client_processing.entite.product.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class ProductMapper {

    public Product toProduct(ProductRequest productRequest) {
        return Product.builder()
                .name(productRequest.getName())
                .createDate(LocalDate.now())
                .key(KeyList.valueOf(productRequest.getKey()))
                .build();
    }
}
