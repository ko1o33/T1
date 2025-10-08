package com.example.client_processing.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class MetricDto {
    String type;
    Long timestamp;
    String value;

}
