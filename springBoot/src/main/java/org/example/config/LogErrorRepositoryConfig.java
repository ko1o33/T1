package org.example.config;

import org.example.service.LogErrorService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LogErrorRepositoryConfig {

    @Bean
    @ConditionalOnMissingBean(name = "createLogErrorRepositoryConfig")
    public LogErrorService createLogErrorRepositoryConfig() {
        return new LogErrorService();
    }
}
