package com.example.client_processing.aop;


import com.example.client_processing.util.CacheEntry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class CachedAspect {

    @Value("${value.limitTime}")
    private Long TTL;

    private static final HashMap<String, CacheEntry> cache = new HashMap<>();


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

    @Pointcut("execution(* com.example.client_processing.repository.UserRepository.findByLogin(String))")
    public void cacheForUserRepositoryFindByLogin() {
    }

    @Pointcut("execution(* com.example.client_processing.repository.UserRepository.findByLoginAndPassword(String, String))")
    public void cacheForUserRepositoryFindByLoginAndPassword() {
    }

    @Pointcut("execution(* com.example.client_processing.repository.ClientRepository.getByClientId(String))")
    public void cacheForClientRepositoryGetByClientId() {
    }

    @Pointcut("execution(* com.example.client_processing.repository.ProductRepository.findByProductId(String))")
    public void cacheForProductRepositoryFindByProductId() {
    }


    @Around("cacheForUserRepositoryFindByLogin()")
    public Object cacheMethodForUserRepositoryFindByLogin(final ProceedingJoinPoint joinPoint) throws Throwable {
        String key = joinPoint.getArgs()[0].toString();
        return saveAndGetToCache(joinPoint, key);
    }

    @Around("cacheForUserRepositoryFindByLoginAndPassword()")
    public Object cacheMethod(final ProceedingJoinPoint joinPoint) throws Throwable {
        String key = joinPoint.getArgs()[0].toString() + joinPoint.getArgs()[1].toString();
        return saveAndGetToCache(joinPoint, key);
    }

    @Around("cacheForClientRepositoryGetByClientId()")
    public Object cacheForClientRepositoryGetByClientId(final ProceedingJoinPoint joinPoint) throws Throwable {
        String key = joinPoint.getArgs()[0].toString();
        return saveAndGetToCache(joinPoint, key);
    }

    @Around("cacheForProductRepositoryFindByProductId()")
    public Object cacheProductRepositoryFindByProductId(final ProceedingJoinPoint joinPoint) throws Throwable {
        String key = joinPoint.getArgs()[0].toString();
        return saveAndGetToCache(joinPoint, key);
    }

    private Object saveAndGetToCache(ProceedingJoinPoint joinPoint, String key) throws Throwable {
        if (cache.containsKey(key) && cache.get(key).checkTime()) {
            log.info(cache.get(key).getValue().toString());
            return cache.get(key).getValue();
        } else {
            Object result = joinPoint.proceed();
            cache.put(key, new CacheEntry(result, TTL));
            return result;
        }
    }

}
