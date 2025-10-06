package com.example.client_processing.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Map;
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

    public void sendKeyTo(String topic, String key, Object o) {
        try {
            template.send(topic, key,o);
        }catch (Exception e){
            log.error(e.getMessage());
        }
        finally {
            template.flush();
        }
    }

    public void sendWithHeadersTo(String topic, String key, Object o, Map<String,String> headers) {
        try {

            ProducerRecord<String, Object> record = new ProducerRecord<>(topic, key, o);
            headers.forEach((keyy,value)->record.headers().add(keyy,value.getBytes(StandardCharsets.UTF_8)));
            template.send(record);
        }catch (Exception e){
            log.error(e.getMessage());
        }
        finally {
            template.flush();
        }
    }
}
