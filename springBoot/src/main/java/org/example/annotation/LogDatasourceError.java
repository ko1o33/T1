package org.example.annotation;





import org.example.entite.aop.TypeError;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LogDatasourceError {
    TypeError type() default TypeError.ERROR;

    String value() default "Error";

    String service() default "";
}
