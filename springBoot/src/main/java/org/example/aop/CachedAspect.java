package org.example.aop;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.example.service.CashedService;


@Aspect
@Slf4j
@RequiredArgsConstructor
public class CachedAspect {

    private final CashedService cashedService;

    @Around("@annotation(org.example.annotation.Cached)")
    public Object cacheProductRepositoryFindByProductId(final ProceedingJoinPoint joinPoint) throws Throwable {
        String key = cashedService.createKey(joinPoint);
        Object value = cashedService.getValue(key);
        if (value != null) {
            log.info("Cached has this value {}", key);
            return value;
        }
        Object result = joinPoint.proceed();
        if (result != null) {
            log.info("Save result for key : {}", key);
            cashedService.saveValue(key, result);
        }
        return result;
    }
}
