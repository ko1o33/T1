package com.example.credit_processing.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Value;

import java.time.LocalDate;

@Value
public class ProductRegistryRequest {

    @NotNull
    Long clientId;

    @NotNull
    Long accountId;

    @NotNull
    String productId;

    @NotNull
    Long monthCount;

    @NotNull
    Long amount;

    @NotNull
    float interestRate;

    @NotNull
    LocalDate openDate;
}
