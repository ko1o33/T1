package com.example.client_processing.entite.client;

import com.example.client_processing.entite.clientProduct.ClientProduct;
import com.example.client_processing.entite.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "clients")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "client_id", nullable = false, length = 128, unique = true)
    private String clientId ;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User userId;

    @Column(name = "first_name" ,nullable = false, length = 128)
    private String firstName;

    @Column(name = "middle_name" ,nullable = false, length = 128)
    private String middleName;

    @Column(name = "last_name" ,nullable = false, length = 128)
    private String lastName;

    @Column(name = "date_of_birth" ,nullable = false, length = 128)
    private LocalDate dateOfBirth;

    @Enumerated(EnumType.STRING)
    @Column(name = "document_type" ,nullable = false, length = 128)
    private DocumentTypeList documentType;

    @Column(name = "document_id" ,nullable = false, length = 128, unique = true)
    private String documentId;

    @Column(name = "document_prefix" ,nullable = false, length = 128)
    private String documentPrefix;

    @Column(name = "document_suffix" ,nullable = false, length = 128)
    private String documentSuffix;
}
