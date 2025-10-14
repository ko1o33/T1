package com.example.account_processing.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.validation.constraints.NotNull;
import lombok.Value;


@Value
@JsonDeserialize
public class TransactionRequest {

    @NotNull
    Long accountId;

    @NotNull
    String cardId;

    @NotNull
    String transactionId;

    @NotNull
    String type;

    @NotNull
    Long amount;

    @NotNull
    String status;
}
