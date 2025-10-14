package com.example.account_processing.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Value;

@Value
public class PaymentDto {

    @NotNull
    Long accountId;

    @NotNull
    Long amount;
}
