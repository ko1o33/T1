package com.example.account_processing.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Value;

@Value
public class CardRequest {
    @NotNull
    Long accountId;

    @NotNull
    String cardId;

    @NotNull
    String paymentSystem;

    @NotNull
    String status;
}
