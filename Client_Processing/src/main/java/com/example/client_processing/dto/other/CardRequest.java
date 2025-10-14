package com.example.client_processing.dto.other;

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
