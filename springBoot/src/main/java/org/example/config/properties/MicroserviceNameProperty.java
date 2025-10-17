package org.example.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;


@Getter
@Setter
@ConfigurationProperties(prefix = "mystarter.value.logproperty")
public class MicroserviceNameProperty {
    private String microserviceName;
}
