package org.example.config;



import org.example.config.properties.CacheProperties;
import org.example.service.CashedService;
import org.example.service.impl.CashedServiceImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(CacheProperties.class)
public class CachedServiceConfig {

    @Bean
    @ConditionalOnMissingBean(name = "t1CashedService")
    public CashedService t1CashedService(
            CacheProperties cacheProperties
    ) {
        return new CashedServiceImpl(cacheProperties.getTtl());
    }

}
