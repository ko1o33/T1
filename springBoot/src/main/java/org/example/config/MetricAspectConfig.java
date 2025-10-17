package org.example.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.aop.MetricAspect;
import org.example.config.properties.CacheProperties;
import org.example.kafka.KafkaProducer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MetricAspectConfig {

    @Bean
    public MetricAspect CreateMetricAspect(
            @Qualifier("createKafkaProducer") KafkaProducer kafkaProducer,
            ObjectMapper objectMapper,
            CacheProperties cacheProperties
            ) {
        return new MetricAspect(kafkaProducer,objectMapper,cacheProperties.getTtl());
    }
}
