package com.example.client_processing.dto.aop;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@Builder
public class HttpRequestLog {
    LocalDateTime timestamp;

    String methodSignature;

    String url;

    Object body;
}
