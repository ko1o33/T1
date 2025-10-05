package com.example.account_processing.service;

import com.example.account_processing.dto.TransactionRequest;

public interface TransactionService {
    void createTransaction(TransactionRequest transactionRequest);
}
