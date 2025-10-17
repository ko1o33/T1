package org.example.config;

import org.example.aop.CachedAspect;
import org.example.service.CashedService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CachedAspectConfig {

    @Bean
    @ConditionalOnMissingBean(name = "Stater.CachedAspect")
    public CachedAspect t1CachedAspect(
            @Qualifier("t1CashedService") CashedService t1CashedService
    ) {
        return new CachedAspect(t1CashedService);
    }
}
