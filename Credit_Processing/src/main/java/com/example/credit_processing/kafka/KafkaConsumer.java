package com.example.credit_processing.kafka;

import com.example.credit_processing.aop.annotation.LogDatasourceError;
import com.example.credit_processing.aop.annotation.Metric;
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

    @KafkaListener(topics = "client_credit_products", groupId = "my_consumer")
    @LogDatasourceError
    @Metric
    public void listen(String json) {
        try {
            log.info("Я получил {}", json);
            productRegistryService.createProduct(json);
        } catch (Exception e) {
            log.error("Ошибка :" + e.getMessage());
        }
    }

}
