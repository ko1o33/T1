package com.example.client_processing.dto.other;

import lombok.Value;

@Value
public class TransactionRequest {
    Long accountId;
    String cardId;
    String transactionId;
    String type;
    Long amount;
    String status;
}
