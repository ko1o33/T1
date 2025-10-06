package com.example.client_processing.aop.annotation;

import com.example.client_processing.aop.TypeError;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface LogDatasourceError {
    TypeError type();
    String value();
    String service() ;
}
