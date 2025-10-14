package com.example.credit_processing.dto.aop;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class MetricDto {
    String type;
    Long timestamp;
    String value;

}
