package com.example.client_processing.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Value;

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

    String status;
}
