package com.example.account_processing.service.impl;

import com.example.account_processing.dto.CardRequest;
import com.example.account_processing.dto.TransactionRequest;
import com.example.account_processing.entite.transaction.StatusList;
import com.example.account_processing.entite.transaction.Transaction;
import com.example.account_processing.entite.transaction.TypeList;
import com.example.account_processing.repository.AccountRepository;
import com.example.account_processing.repository.CardRepository;
import com.example.account_processing.service.AccountService;
import com.example.account_processing.service.TransactionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionServiceImpl implements TransactionService {

    private final ObjectMapper objectMapper;
    private final AccountRepository accountRepository;
    private final CardRepository cardRepository;

    public void createTransaction(String json) {
        try {
            var transactionRequest = objectMapper.readValue(json, TransactionRequest.class);
            var transaction = Transaction.builder()
                    .type(TypeList.valueOf(transactionRequest.getType()))
                    .amount(transactionRequest.getAmount())
                    .account(accountRepository.getById(transactionRequest.getAccountId()))
                    .card(cardRepository.getByCardId(transactionRequest.getCardId()))
                    .status(StatusList.valueOf(transactionRequest.getStatus()))
                    .timestamp(Lo)
                    .build();
        }catch (Exception e){
            log.info(e.getMessage());
        }
    }
}
