package org.example.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;


@Getter
@Setter
@ConfigurationProperties(prefix = "mystarter.kafka")
public class KafkaProperties {

    private Bootstrap bootstrap = new Bootstrap();
    private Topic topic = new Topic();

    @Getter
    @Setter
    public static class Bootstrap {
        private String url;
    }

    @Getter
    @Setter
    public static class Topic {
        private String serviceLogs;
    }
}
