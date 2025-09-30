package com.example.credit_processing.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;

@Value
@JsonInclude
@Builder
@AllArgsConstructor
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
