package com.example.client_processing.entite.clientProduct;

import com.example.client_processing.entite.client.Client;
import com.example.client_processing.entite.product.Product;
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
@Table(name = "client_products")
public class ClientProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    public Client client;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Product product;

    @Column(name = "open_date" ,nullable = false, length = 128)
    private LocalDate openDate;

    @Column(name = "close_date" ,nullable = false, length = 128)
    private LocalDate closeDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 128)
    private StatusList status;
}
