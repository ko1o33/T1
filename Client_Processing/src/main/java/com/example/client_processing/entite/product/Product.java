package com.example.client_processing.entite.product;

import com.example.client_processing.entite.client.Client;
import com.example.client_processing.entite.clientProduct.ClientProduct;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 128)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 128)
    private KeyList key;

    @Column(name = "create_date" ,nullable = false, length = 128)
    private LocalDate createDate;

    @Column(name = "product_id")
    private String productId;
}
