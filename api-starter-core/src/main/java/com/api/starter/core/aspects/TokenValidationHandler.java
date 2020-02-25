package com.api.starter.core.aspects;

import com.api.starter.core.annotations.TokenValidation;
import com.api.starter.core.utils.SpringContextHolder;
import com.api.starter.util.utils.JwtUtils;
import com.api.starter.util.utils.ResponseEntity;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Aspect
@Service
@Order(0)
public class TokenValidationHandler {

    @Pointcut()
    public void validaToken() {
    }

    @Around("validaToken()")
    public Object handler(ProceedingJoinPoint pjp) throws Throwable{

        Signature signature = pjp.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;

        TokenValidation annotation = methodSignature.getMethod().getAnnotation(TokenValidation.class);
        if (null != annotation && !annotation.validation()) {
            return pjp.proceed();
        }
        HttpServletRequest request = SpringContextHolder.getRequest();
        String token = request.getHeader("token");
        if (!StringUtils.hasText(token)) {
            return ResponseEntity.FAIL("token为空",50008);
        }
        String user;
        try {
             user = JwtUtils.parse(token);
        } catch (Exception e) {
            return ResponseEntity.FAIL("无效的token",50014);
        }
        request.setAttribute("user",user);
        return pjp.proceed();
    }
}
