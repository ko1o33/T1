package org.example.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.aop.LogAspect;
import org.example.kafka.KafkaProducer;
import org.example.service.LogErrorService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import javax.sql.DataSource;

@Configuration
public class LogAspectConfig {

    @Bean
    @ConditionalOnMissingBean(name = "LogAspectConfig")
    public LogAspect LogAspectConfig(
            @Qualifier("createKafkaProducer")KafkaProducer kafkaProducer,
            ObjectMapper objectMapper
            ) {
        return new LogAspect(kafkaProducer,null,objectMapper);
    }
}
