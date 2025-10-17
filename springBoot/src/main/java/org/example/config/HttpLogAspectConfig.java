package org.example.config;

import org.example.aop.HttpLogAspect;
import org.example.config.properties.MicroserviceNameProperty;
import org.example.kafka.KafkaProducer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@EnableConfigurationProperties(MicroserviceNameProperty.class)
public class HttpLogAspectConfig {

    @Bean
    @ConditionalOnMissingBean(name = "t1HttpLogAspect")
    public HttpLogAspect t1HttpLogAspect(
            @Qualifier("createKafkaProducer") KafkaProducer kafkaProducer,
            MicroserviceNameProperty microserviceName
            ) {
        return new HttpLogAspect(microserviceName.getMicroserviceName(),kafkaProducer);
    }
}
