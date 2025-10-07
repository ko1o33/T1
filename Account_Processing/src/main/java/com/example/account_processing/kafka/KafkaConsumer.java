package com.example.account_processing.kafka;

import com.example.account_processing.dto.PaymentDto;
import com.example.account_processing.dto.TransactionRequest;
import com.example.account_processing.repository.TransactionRepository;
import com.example.account_processing.service.AccountService;
import com.example.account_processing.service.CardService;
import com.example.account_processing.service.PaymentService;
import com.example.account_processing.service.TransactionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaConsumer {

    private final AccountService accountService;
    private final CardService cardService;
    private final TransactionService transactionService;
    private final ObjectMapper objectMapper;
    private final PaymentService paymentService;

    @KafkaListener(topics = "client_products", groupId = "create-account")
    public void listenAccount(String json) {
        try {
            accountService.createAccount(json);
        }catch (Exception e){
            log.error(e.getMessage());
        }
    }

    @KafkaListener(topics = "client_transactions", groupId = "my_consumer")
    public void listenTransactions(String json,
                                   @Header(KafkaHeaders.RECEIVED_KEY) String key) {
        log.info("Received transaction. Key: {}, Event: {}", key, json);
        try {
            var transactionRequest = objectMapper.readValue(json, TransactionRequest.class);
            if(transactionRequest.getAccountId().equals(transactionRequest.getAccountId())) {}
            transactionService.createTransaction(transactionRequest);
        }catch (Exception e){
            log.error(e.getMessage());
        }
    }

    @KafkaListener(topics = "client_cards", groupId = "my_consumer")
    public void listenCards(String json) {
        try {
            cardService.createCard(json);
        }catch (Exception e){
            log.error(e.getMessage());
        }
    }

    @KafkaListener(topics = "client_payments", groupId = "my_consumer")
    public void listenPayment(String json,
                              @Header(KafkaHeaders.RECEIVED_KEY) String key) {
        log.info("Received payments. Key: {}, Event: {}", key, json);
        try {
            var payment = objectMapper.readValue(json, PaymentDto.class);
            paymentService.paymentCredit(payment);
        }catch (Exception e){
            log.error(e.getMessage());
        }
    }


}
