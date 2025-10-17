package org.example.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;

@Configuration
public class ObjectMapperConfig {
    @Bean
    @ConditionalOnMissingBean(name = "CreateObjectMapper")
    public ObjectMapper CreateObjectMapper() {
        return new ObjectMapper();
    }
}
