package com.example.credit_processing.aop;

import com.example.credit_processing.aop.annotation.HttpIncomeRequestLog;
import com.example.credit_processing.aop.annotation.HttpOutcomeRequestLog;
import com.example.credit_processing.dto.aop.HttpRequestLog;
import com.example.credit_processing.kafka.KafkaProducer;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class HttpLogAspect {

    private final KafkaProducer kafkaProducer;
    private final ObjectMapper objectMapper;

    @AfterReturning(value = "@annotation(httpOutcomeRequestLog)")
    public void sendMessage(JoinPoint joinPoint, HttpOutcomeRequestLog httpOutcomeRequestLog) {
        String key = httpOutcomeRequestLog.service();
        Map<String, String> headers = new HashMap<>();
        headers.put("type", httpOutcomeRequestLog.type().toString());
        try {
            var value = HttpRequestLog.builder()
                    .timestamp(LocalDateTime.now())
                    .url(getUrl(joinPoint))
                    .methodSignature(joinPoint.getSignature().toString())
                    .body(objectMapper.writeValueAsString(joinPoint.getArgs()))
                    .build();
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
        try {
            var value = HttpRequestLog.builder()
                    .timestamp(LocalDateTime.now())
                    .url(getUrl(joinPoint))
                    .methodSignature(joinPoint.getSignature().toString())
                    .body(objectMapper.writeValueAsString(joinPoint.getArgs()))
                    .build();
            kafkaProducer.sendWithHeadersTo("service_logs", key, value, headers);
        }catch (Exception e){
            log.error("sendMessage:{}",e.getMessage());
        }
    }

    private String getUrl(JoinPoint joinPoint) {
        try {
            Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();

            String classPath = "";
            Class<?> controllerClass = method.getDeclaringClass();
            if (controllerClass.isAnnotationPresent(RequestMapping.class)) {
                RequestMapping classMapping = controllerClass.getAnnotation(RequestMapping.class);
                if (classMapping.value().length > 0) {
                    classPath = classMapping.value()[0];
                }
            }

            String methodPath = "";
            if (method.isAnnotationPresent(GetMapping.class)) {
                methodPath = method.getAnnotation(GetMapping.class).value()[0];
            } else if (method.isAnnotationPresent(PostMapping.class)) {
                methodPath = method.getAnnotation(PostMapping.class).value()[0];
            }

            return classPath + (methodPath.startsWith("/") ? methodPath : "/" + methodPath);

        } catch (Exception ex) {
            return "unknown-url";
        }
    }


}
