package org.example.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.aop.LogAspect;
import org.example.kafka.KafkaProducer;
import org.example.repository.LogErrorRepository;
import org.example.service.LogErrorService;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.CrudRepository;

import javax.sql.DataSource;

@Configuration
public class LogAspectConfig {

    @Bean
    @ConditionalOnMissingBean(name = "LogAspectConfig")
    public LogAspect LogAspectConfig(
            @Qualifier("createKafkaProducer")KafkaProducer kafkaProducer,
            @Qualifier("createLogErrorRepositoryConfig")LogErrorService logErrorService,
            ObjectMapper objectMapper
            ) {
        return new LogAspect(kafkaProducer,logErrorService,objectMapper);
    }
}
