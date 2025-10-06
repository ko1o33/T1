package com.example.client_processing.aop;


import com.example.client_processing.aop.annotation.LogDatasourceError;
import com.example.client_processing.dto.LogError;
import com.example.client_processing.entite.LogErrorEntity;
import com.example.client_processing.kafka.KafkaProducer;
import com.example.client_processing.repository.LogErrorRepository;
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
public class LogAspect {

    private final KafkaProducer kafkaProducer;
    private final LogErrorRepository logErrorRepository;

    @AfterThrowing(pointcut = "@annotation(logDatasourceError)",
            throwing = "e")
    public void log(JoinPoint joinPoint, Exception e, LogDatasourceError logDatasourceError) {
        String key = logDatasourceError.service();
        var stringWriter = new StringWriter();
        var printWriter = new PrintWriter(stringWriter);
        e.printStackTrace(printWriter);
        Map<String, String> headers = new HashMap<>();
        headers.put("type", logDatasourceError.type().toString());
        headers.put("value", logDatasourceError.value());
        headers.put("service", logDatasourceError.service());
        try {
            var log = LogError.builder()
                    .timestamp(LocalDateTime.now())
                    .stackTrace(printWriter.toString())
                    .methodSignature(joinPoint.getSignature().toString())
                    .exceptionMessage(e.getMessage())
                    .inputParameters(joinPoint.getArgs().toString())
                    .build();
            kafkaProducer.sendWithHeadersTo("service_logs", key, log, headers);
        }catch (Exception ex){
            var logBD = LogErrorEntity.builder()
                    .timestamp(LocalDateTime.now())
                    .stackTrace(printWriter.toString())
                    .methodSignature(joinPoint.getSignature().toString())
                    .exceptionMessage(e.getMessage())
                    .inputParameters(joinPoint.getArgs().toString())
                    .build();
            logErrorRepository.save(logBD);
            log.error(ex.getMessage(),ex + "message: "+ logBD);
        }
    }

}
