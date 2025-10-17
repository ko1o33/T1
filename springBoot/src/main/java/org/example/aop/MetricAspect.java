package org.example.aop;


import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.example.annotation.Metric;
import org.example.dto.aop.MetricDto;
import org.example.kafka.KafkaProducer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class MetricAspect {

    private final KafkaProducer kafkaProducer;
    private final ObjectMapper objectMapper;
    private final Long limit;

    @Around("@annotation(metric)")
    public Object metrics(ProceedingJoinPoint joinPoint, Metric metric) throws Throwable {
        log.info("Вызов метода: {}", joinPoint.getSignature().toShortString());
        long beforeTime = System.currentTimeMillis();
        try {
            var result = joinPoint.proceed();
            return result;
        } finally {
            long afterTime = System.currentTimeMillis();
            var time = afterTime - beforeTime;
            log.info("Время исполнения: {} ms", time);
            if (limit < time) {
                var log = MetricDto.builder()
                        .timestamp(time)
                        .type("WARNING")
                        .value(objectMapper.writeValueAsString(objectMapper.writeValueAsString(joinPoint.getArgs())))
                        .build();
                kafkaProducer.sendTo("service_logs", log);
            }
        }
    }

}
