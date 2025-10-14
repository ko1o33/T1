package com.example.service_log.kafka;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaConsumer {


    @KafkaListener(topics = "service_logs",groupId = "my_consumer")
    public void listen(String json) {
        try {
            System.out.println(json.toString());
        }catch (Exception e){
            log.error(e.getMessage());
        }
    }

}
