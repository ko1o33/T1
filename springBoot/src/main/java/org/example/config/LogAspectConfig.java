package org.example.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.aop.LogAspect;
import org.example.kafka.KafkaProducer;
import org.example.repository.LogErrorRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@Configuration
@EnableJpaRepositories({"org.example.repository",
    "com.example.*.repository"})
@EntityScan({"org.example.entite.aop",
        "com.example.*.entite"})
public class LogAspectConfig {

    @Bean
    @ConditionalOnMissingBean(name = "LogAspectConfig")
    public LogAspect LogAspectConfig(
            @Qualifier("createKafkaProducer")KafkaProducer kafkaProducer,
            LogErrorRepository logErrorRepository,
            ObjectMapper objectMapper
            ) {
        return new LogAspect(kafkaProducer,logErrorRepository,objectMapper);
    }
}
