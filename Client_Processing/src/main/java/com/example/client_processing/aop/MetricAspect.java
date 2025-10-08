package com.example.client_processing.aop;

import com.example.client_processing.aop.annotation.Metric;
import com.example.client_processing.dto.MetricDto;
import com.example.client_processing.kafka.KafkaProducer;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicLong;

@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class MetricAspect {

    private final KafkaProducer kafkaProducer;
    private final ObjectMapper objectMapper;

    private static final AtomicLong START_TIME = new AtomicLong();
    @Value("${value.limitTime}")
    private Long limit;

    @Around("@annotation(metric)")
    public Object Object(ProceedingJoinPoint joinPoint, Metric metric) throws Throwable {
        log.info("Вызов метода: {}", joinPoint.getSignature().toShortString());
        long beforeTime = System.currentTimeMillis();
        Object result = null;
        try {
            result = joinPoint.proceed();
        } finally {
            long afterTime = System.currentTimeMillis();
            var time = afterTime - beforeTime;
            log.info("Время исполнения: {} ms", time);
            if(limit<time){
                var log = MetricDto.builder()
                        .timestamp(time)
                        .type("WARNING")
                        .value(objectMapper.writeValueAsString(objectMapper.writeValueAsString(joinPoint.getArgs())))
                        .build();
                kafkaProducer.sendTo("service_logs",log);
            }
        }
        return result;
    }

}
