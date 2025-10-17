package org.example.aop;


import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.example.entite.aop.TypeError;
import org.example.kafka.KafkaProducer;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class HttpLogAspect {

    private final String microserviceName;
    private final KafkaProducer kafkaProducer;

    @AfterReturning(value = "@annotation(org.example.annotation.HttpOutcomeRequestLog)",
            returning = "result")
    public void outcomeRequestLog(JoinPoint joinPoint, Object result) {
        var headers = createHeadersOutcome(joinPoint, result);
        sendMessageToKafka(headers);
        log.info("Info send to service_logs");
    }

    @Before("@annotation(org.example.annotation.HttpIncomeRequestLog)")
    public void incomeRequestLog(JoinPoint joinPoint) {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (servletRequestAttributes != null) {
            HttpServletRequest request = servletRequestAttributes.getRequest();
            var headers = createHeadersIncome(joinPoint, request);
            sendMessageToKafka(headers);
            log.info("Info send to service_logs");
        }
    }

    public void sendMessageToKafka(Map<String, String> headers) {
        try {
            kafkaProducer.sendWithHeadersTo("service_logs", microserviceName, TypeError.INFO, headers);
        } catch (Exception e) {
            log.error("sendMessage error: {}", e.getMessage());
        }
    }

    public Map<String, String> createHeadersIncome(JoinPoint joinPoint, HttpServletRequest request) {
        Map<String, String> map = new HashMap<>();
        map.put("timestamp", LocalDateTime.now().toString());
        map.put("method", joinPoint.getSignature().toShortString());
        map.put("URI", request.getRequestURI());
        map.put("params", request.getParameterMap().toString());
        map.put("args", Arrays.toString(joinPoint.getArgs()));
        return map;
    }

    public Map<String, String> createHeadersOutcome(JoinPoint joinPoint, Object result) {
        Map<String, String> map = new HashMap<>();
        map.put("timestamp", LocalDateTime.now().toString());
        map.put("method", joinPoint.getSignature().toShortString());
        map.put("URI", getUrl(joinPoint));
        map.put("result", result.toString());
        map.put("args", Arrays.toString(joinPoint.getArgs()));
        return map;
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
            } else if (method.isAnnotationPresent(PatchMapping.class)) {
                methodPath = method.getAnnotation(PatchMapping.class).value()[0];
            } else if (method.isAnnotationPresent(DeleteMapping.class)) {
                methodPath = method.getAnnotation(DeleteMapping.class).value()[0];
            }
            return classPath + (methodPath.startsWith("/") ? methodPath : "/" + methodPath);
        } catch (Exception ex) {
            log.info("getUrl error: {}", ex.getMessage());
            return "Happened mistake";
        }
    }


}
