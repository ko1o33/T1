package com.example.client_processing.controller.clientProductController;


import com.example.client_processing.dto.other.AccountAndProductRequest;
import com.example.client_processing.exception.MyException;
import com.example.client_processing.kafka.KafkaProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.annotation.HttpIncomeRequestLog;
import org.example.annotation.HttpOutcomeRequestLog;
import org.example.annotation.LogDatasourceError;
import org.example.annotation.Metric;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.regex.Pattern;

@RestController
@RequiredArgsConstructor
@RequestMapping("/account")
@Slf4j
public class AccountController {

    private final KafkaProducer kafkaProducer;

    private final Set<String> set1 = new HashSet<>(Arrays.asList("DC", "CC", "NS", "PENS"));
    private final Set<String> set2 = new HashSet<>(Arrays.asList("IPO", "PC", "AC"));
    private final Pattern pattern = Pattern.compile("([A-Za-z]+)(\\d+)");

    @PostMapping("/create")
    @LogDatasourceError
    @Metric
    @HttpIncomeRequestLog
    @HttpOutcomeRequestLog
    public ResponseEntity<?> accountCreate(@RequestBody AccountAndProductRequest account) {
        try {
            log.info("Creating account {}", account);
            var mather = pattern.matcher(account.getProductId());
            mather.find();
            if (set1.contains(mather.group(1))) {
                kafkaProducer.sendTo("client_products", account);
                return ResponseEntity.ok("Запрос отправлен в микросервис client_products");
            } else if (set2.contains(mather.group(1))) {
                kafkaProducer.sendTo("client_credit_products", account);
                return ResponseEntity.ok("Запрос отправлен в микросервис client_credit_products");
            }
            throw new MyException("Такой тип продукта запрещен");
        } catch (Exception e) {
            log.info(e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Ошибка " + e.getMessage());
        }
    }
}
