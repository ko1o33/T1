package com.example.client_processing.dto.aop;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@Builder
public class LogError {

    LocalDateTime timestamp;

    String methodSignature;

    String stackTrace;

    String exceptionMessage;

    Object inputParameters;
}
