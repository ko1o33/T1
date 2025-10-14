package com.example.client_processing.dto.other;

import jakarta.validation.constraints.NotNull;
import lombok.Value;

@Value
public class PaymentCreditDto {

    @NotNull
    Long accountId;

    @NotNull
    Long amount;
}
