package com.example.client_processing.aop;

import com.example.client_processing.aop.annotation.HttpIncomeRequestLog;
import com.example.client_processing.aop.annotation.HttpOutcomeRequestLog;
import com.example.client_processing.dto.HttpRequestLog;
import com.example.client_processing.dto.LogError;
import com.example.client_processing.kafka.KafkaProducer;
import com.example.client_processing.repository.LogErrorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class HttpLogAspect {

    private final KafkaProducer kafkaProducer;
    private final LogErrorRepository logErrorRepository;

    @AfterReturning("@annotation(httpOutcomeRequestLog)")
    public void sendMessage(JoinPoint joinPoint, HttpOutcomeRequestLog httpOutcomeRequestLog) {
        String key = httpOutcomeRequestLog.service();
        Map<String, String> headers = new HashMap<>();
        headers.put("type", httpOutcomeRequestLog.type().toString());
        var value = HttpRequestLog.builder()
                .timestamp(LocalDateTime.now())
                .url(httpOutcomeRequestLog.url())
                .methodSignature(joinPoint.getSignature().toString())
                .build();
        try {
            kafkaProducer.sendWithHeadersTo("service_logs", key, value, headers);
        }catch (Exception e){
            log.error("sendMessage:{}",e.getMessage());
        }
    }

    @Before("@annotation(httpIncomeRequestLog)")
    public void beforeMessage(JoinPoint joinPoint, HttpIncomeRequestLog httpIncomeRequestLog) {
        String key = httpIncomeRequestLog.service();
        Map<String, String> headers = new HashMap<>();
        headers.put("type", httpIncomeRequestLog.type().toString());
        var value = HttpRequestLog.builder()
                .timestamp(LocalDateTime.now())
                .url(httpIncomeRequestLog.url())
                .methodSignature(joinPoint.getSignature().toString())
                .build();
        try {
            kafkaProducer.sendWithHeadersTo("service_logs", key, value, headers);
        }catch (Exception e){
            log.error("sendMessage:{}",e.getMessage());
        }

    }



}
