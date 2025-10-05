package com.example.account_processing.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Value;


@Value
@JsonDeserialize
public class TransactionRequest {
    Long accountId;
    String cardId;
    String transactionId;
    String type;
    Long amount;
    String status;
}
