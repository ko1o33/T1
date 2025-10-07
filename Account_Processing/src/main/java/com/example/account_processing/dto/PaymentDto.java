package com.example.account_processing.dto;

import lombok.Value;

@Value
public class PaymentDto {
    Long accountId;
    Long amount;
}
