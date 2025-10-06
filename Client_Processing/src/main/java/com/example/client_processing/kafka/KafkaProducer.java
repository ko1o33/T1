package com.example.client_processing.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Component
public class KafkaProducer {

    public final KafkaTemplate<String,Object> template;

    public void sendTo(String topic, Object o) {
        try {
            template.send(topic, UUID.randomUUID().toString() ,o);
        }catch (Exception e){
            log.error(e.getMessage());
        }
        finally {
            template.flush();
        }
    }
}
