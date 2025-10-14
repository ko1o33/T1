package com.example.client_processing.controller.clientProductController;

import com.example.client_processing.aop.annotation.HttpIncomeRequestLog;
import com.example.client_processing.aop.annotation.HttpOutcomeRequestLog;
import com.example.client_processing.aop.annotation.LogDatasourceError;
import com.example.client_processing.aop.annotation.Metric;
import com.example.client_processing.dto.other.PaymentCreditDto;
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
@RequestMapping("/client")
@Slf4j
public class ClientPaymentController {

    private final KafkaProducer kafkaProducer;

    @PostMapping("/paymentCredit")
    @LogDatasourceError
    @Metric
    @HttpIncomeRequestLog
    @HttpOutcomeRequestLog
    public ResponseEntity<?> paymentAllCredit(@RequestBody @Valid PaymentCreditDto paymentDto) {
        try {
            kafkaProducer.sendTo("client_payments", paymentDto);
            log.info("Отправлен paymentDto {} в client_payments ", paymentDto);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.info("произошла ошибка " + e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

}
