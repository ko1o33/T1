package com.example.client_processing.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Value;

@Value
public class AccountRequest {
    @NotNull
    String clientId;

    @NotNull
    String productId;

    @NotNull
    Long balance;

    @NotNull
    float interestRate;

    @NotNull
    Boolean isRecalc;

    @NotNull
    Boolean cardExist;

    @NotNull
    String status;
}
