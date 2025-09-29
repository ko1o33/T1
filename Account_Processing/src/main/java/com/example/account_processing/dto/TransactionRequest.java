package com.example.account_processing.dto;

import lombok.Value;

import java.time.LocalDate;

@Value
public class TransactionRequest {
    Long accountId;
    String cardId;
    String transactionId;
    String type;
    Long amount;
    String status;
    LocalDate timestamp;
}
