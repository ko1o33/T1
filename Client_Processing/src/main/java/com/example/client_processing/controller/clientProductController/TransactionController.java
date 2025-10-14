package com.example.client_processing.controller.clientProductController;

import com.example.client_processing.aop.annotation.HttpIncomeRequestLog;
import com.example.client_processing.aop.annotation.HttpOutcomeRequestLog;
import com.example.client_processing.aop.annotation.LogDatasourceError;
import com.example.client_processing.aop.annotation.Metric;
import com.example.client_processing.dto.other.TransactionRequest;
import com.example.client_processing.kafka.KafkaProducer;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/transaction")
@Slf4j
public class TransactionController {

    private final KafkaProducer kafkaProducer;

    @PostMapping("/create")
    @LogDatasourceError
    @Metric
    @HttpIncomeRequestLog
    @HttpOutcomeRequestLog
    public ResponseEntity<?> createTransaction(@Valid @RequestBody TransactionRequest transactionRequest) {
        try {
            log.info("Received request to create transaction : {}", transactionRequest);
            kafkaProducer.sendTo("client_transactions", transactionRequest);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.info("Произошла ошибка " + e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
}
