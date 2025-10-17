package org.example.service;

import org.aspectj.lang.ProceedingJoinPoint;

public interface CashedService {
    String createKey(ProceedingJoinPoint joinPoint);

    void saveValue(String key, Object value);

    Object getValue(String key);
}
