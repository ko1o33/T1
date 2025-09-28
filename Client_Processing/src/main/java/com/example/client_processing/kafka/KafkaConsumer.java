package com.example.client_processing.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {
    @KafkaListener(topics = "client", groupId = "my_consumer")
    public void listen(String message) {
        System.out.println(message);
    }

    @KafkaListener(topics = "client", groupId = "my_consumer")
    public void dasd(String message) {
        System.out.println(message);
    }


}
