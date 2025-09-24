package com.example.client_processing.entite.clientProduct;

import com.example.client_processing.entite.client.Client;
import com.example.client_processing.entite.product.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    @JoinColumn(name = "client_id")
    public Client client;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "open_date" ,nullable = false, length = 128)
    private Date openDate;

    @Column(name = "close_date" ,nullable = false, length = 128)
    private Date closeDate;

    @Column(nullable = false, length = 128)
    private StatusList status;
}
