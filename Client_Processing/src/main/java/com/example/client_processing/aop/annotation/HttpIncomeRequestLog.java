package com.example.client_processing.aop.annotation;

import com.example.client_processing.aop.TypeError;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface HttpIncomeRequestLog {
    TypeError type() default TypeError.INFO;
    String value();
    String service() ;
    String url();
}
