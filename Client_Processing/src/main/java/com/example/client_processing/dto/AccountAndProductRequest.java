package com.example.client_processing.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Value;

import java.time.LocalDate;

@Value
public class AccountAndProductRequest {

    @NotNull
    String clientId;

    String productId;

    Long accountId;

    Long amount;

    Long balance;

    Long monthCount;

    float interestRate;

    Boolean isRecalc;

    Boolean cardExist;

    LocalDate openDate;

    String status;
}
