package com.example.client_processing.aop;


import com.example.client_processing.aop.annotation.LogDatasourceError;
import com.example.client_processing.dto.aop.LogError;
import com.example.client_processing.entite.aop.LogErrorEntity;
import com.example.client_processing.kafka.KafkaProducer;
import com.example.client_processing.repository.LogErrorRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.io.PrintWriter;
import java.io.StringWriter;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
@Data
public class LogAspect {

    private final KafkaProducer kafkaProducer;
    private final LogErrorRepository logErrorRepository;
    private final ObjectMapper objectMapper;

    @AfterThrowing(pointcut = "@annotation(logDatasourceError)",
            throwing = "e")
    public void log(JoinPoint joinPoint, Exception e, LogDatasourceError logDatasourceError) {
        String key = logDatasourceError.service();
        var stringWriter = new StringWriter();
        var printWriter = new PrintWriter(stringWriter);
        e.printStackTrace(printWriter);
        printWriter.flush();
        Map<String, String> headers = new HashMap<>();
        headers.put("type", logDatasourceError.type().toString());
        headers.put("value", logDatasourceError.value());
        headers.put("service", logDatasourceError.service());
        try {
            var log = LogError.builder()
                    .timestamp(LocalDateTime.now())
                    .stackTrace(stringWriter.toString())
                    .methodSignature(joinPoint.getSignature().toString())
                    .exceptionMessage(e.getMessage())
                    .inputParameters(objectMapper.writeValueAsString(joinPoint.getArgs()))
                    .build();
            kafkaProducer.sendWithHeadersTo("service_logs", key, log, headers);
        } catch (Exception ex) {
            var logBD = LogErrorEntity.builder()
                    .timestamp(LocalDateTime.now())
                    .stackTrace(printWriter.toString())
                    .methodSignature(joinPoint.getSignature().toString())
                    .exceptionMessage(e.getMessage())
                    .inputParameters(joinPoint.getArgs().toString())
                    .build();
            logErrorRepository.save(logBD);
            log.error(ex.getMessage(), ex + "message: " + logBD);
        }
    }
}
