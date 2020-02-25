package com.api.starter.core.aspects;

import com.api.starter.util.utils.ResponseEntity;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

@Slf4j
@Aspect
@Service
@Order
public class ExceptionHandler {

    @Pointcut()
    public void exceptionHandler() {
    }

    @Around("exceptionHandler()")
    public Object exceptionHandler(ProceedingJoinPoint pjp) {
        try {
            return pjp.proceed();
        } catch (Throwable t) {
            log.error("服务器发生异常-[{}]-[{}]","ExceptionHandler.exceptionHandler",t);
            return ResponseEntity.FAIL("服务器异常");
        }
    }
}
