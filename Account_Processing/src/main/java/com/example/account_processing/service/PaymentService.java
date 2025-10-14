package com.example.account_processing.service;

import com.example.account_processing.dto.PaymentDto;

public interface PaymentService {
    void checkPayment();

    void paymentCredit(PaymentDto paymentDto);
}
