package com.example.account_processing.kafka;


import com.example.account_processing.dto.PaymentDto;
import com.example.account_processing.dto.TransactionRequest;
import com.example.account_processing.service.AccountService;
import com.example.account_processing.service.CardService;
import com.example.account_processing.service.PaymentService;
import com.example.account_processing.service.TransactionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.annotation.LogDatasourceError;
import org.example.annotation.Metric;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
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
    @LogDatasourceError
    @Metric
    public void listenAccount(String json, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        try {
            log.info("Получено собщение из топика {} и собхение - {}", topic, json);
            accountService.createAccount(json);
        } catch (Exception e) {
            log.error("Произошла ошибка в listenAccount " + e.getMessage());
        }
    }

    @KafkaListener(topics = "client_transactions", groupId = "my_consumer")
    @LogDatasourceError
    @Metric
    public void listenTransactions(String json,
                                   @Header(KafkaHeaders.RECEIVED_KEY) String key) {
        log.info("Received transaction. Key: {}, Event: {}", key, json);
        try {
            var transactionRequest = objectMapper.readValue(json, TransactionRequest.class);
            transactionService.createTransaction(transactionRequest);
        } catch (Exception e) {
            log.error("Произошла ошибка в listenTransactions " + e.getMessage());
        }
    }

    @KafkaListener(topics = "client_cards", groupId = "my_consumer")
    @LogDatasourceError
    @Metric
    public void listenCards(String json) {
        try {
            log.info("Received transaction. json: {}", json);
            cardService.createCard(json);
        } catch (Exception e) {
            log.error("Произошла ошибка в listenCards " + e.getMessage());
        }
    }

    @KafkaListener(topics = "client_payments", groupId = "my_consumer")
    @LogDatasourceError
    @Metric
    public void listenPayment(String json,
                              @Header(KafkaHeaders.RECEIVED_KEY) String key) {
        log.info("Received payments. Key: {}, Event: {}", key, json);
        try {
            var payment = objectMapper.readValue(json, PaymentDto.class);
            paymentService.paymentCredit(payment);
        } catch (Exception e) {
            log.error("Произошла ошибка в listenPayment " + e.getMessage());
        }
    }


}
