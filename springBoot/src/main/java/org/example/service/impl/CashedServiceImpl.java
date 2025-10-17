package org.example.service.impl;


import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.aspectj.lang.ProceedingJoinPoint;
import org.example.service.CashedService;
import org.example.util.CacheEntry;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Slf4j
public class CashedServiceImpl implements CashedService {

    private static final HashMap<String, CacheEntry> cache = new HashMap<>();

    private final Long TTL;

    @Scheduled(fixedRate = 30, timeUnit = TimeUnit.MINUTES)
    public void clearCacheMap() {
        Iterator<Map.Entry<String, CacheEntry>> iterator = cache.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, CacheEntry> entry = iterator.next();
            if (!entry.getValue().checkTime()) {
                iterator.remove();
            }
        }
    }

    @Override
    public String createKey(ProceedingJoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();
        if (args == null || args.length == 0) {
            return className + "." + methodName;
        }
        StringBuilder keyBuilder = new StringBuilder();
        keyBuilder.append(className).append(".").append(methodName).append(".");
        for (Object arg : args) {
            if (arg == null) {
                keyBuilder.append("null");
            } else if (BeanUtils.isSimpleValueType(arg.getClass())) {
                keyBuilder.append(arg);
            } else if (arg.getClass().isArray()) {
                keyBuilder.append(Arrays.deepHashCode(new Object[]{arg}));
            } else {
                keyBuilder.append(arg.hashCode());
            }
            keyBuilder.append("-");
        }
        return keyBuilder.toString();
    }

    @Override
    public void saveValue(String key, Object value) {
        cache.put(key, new CacheEntry(value, TTL));
    }

    @Override
    public Object getValue(String key) {
        val value = cache.get(key);
        if (value == null) return null;
        return cache.get(key).getValue();
    }
}
