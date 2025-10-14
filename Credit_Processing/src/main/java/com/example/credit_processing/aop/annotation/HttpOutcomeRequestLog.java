package com.example.credit_processing.aop.annotation;



import com.example.credit_processing.aop.TypeError;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface HttpOutcomeRequestLog {
    TypeError type() default TypeError.INFO;

    String service() default "";
}
