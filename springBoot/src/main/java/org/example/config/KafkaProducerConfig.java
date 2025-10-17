package org.example.config;

import org.example.kafka.KafkaProducer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;

@Configuration
public class KafkaProducerConfig{

    @Bean
    @ConditionalOnMissingBean(name = "createKafkaProducer")
    public KafkaProducer createKafkaProducer(
            @Qualifier("kafkaTemplate")KafkaTemplate<String, Object> template){
        return new KafkaProducer(template);
    }

}
