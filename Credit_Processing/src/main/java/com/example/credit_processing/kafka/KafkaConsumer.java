package com.example.credit_processing.kafka;

import com.example.credit_processing.service.ProductRegistryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaConsumer {

    private final ProductRegistryService productRegistryService;

    @KafkaListener(topics = "client_credit_products")
    public void listen(String json) {
        try {
            productRegistryService.createProduct(json);
        }catch (Exception e){
            log.error(e.getMessage());
        }
    }



}
