package com.example.client_processing.aop;

import com.example.client_processing.kafka.KafkaProducer;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class CachedAspect {


    private final KafkaProducer kafkaProducer;
    private final ObjectMapper objectMapper;


    public void metrics(){

    }
}
