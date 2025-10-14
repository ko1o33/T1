package com.example.client_processing.controller.clientProductController;

import com.example.client_processing.aop.annotation.HttpIncomeRequestLog;
import com.example.client_processing.aop.annotation.HttpOutcomeRequestLog;
import com.example.client_processing.aop.annotation.LogDatasourceError;
import com.example.client_processing.aop.annotation.Metric;
import com.example.client_processing.dto.other.CardRequest;
import com.example.client_processing.exception.MyException;
import com.example.client_processing.kafka.KafkaProducer;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/card")
@Slf4j
public class CardController {

    private final KafkaProducer kafkaProducer;

    @PostMapping("/create")
    @LogDatasourceError
    @Metric
    @HttpIncomeRequestLog
    @HttpOutcomeRequestLog
    public ResponseEntity<?> createCard(@Valid @RequestBody CardRequest cardRequest) {
        try {
            log.info("Создание card : " + cardRequest);
            kafkaProducer.sendTo("client_cards", cardRequest);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("Ошибка в createCard : " + e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

}
