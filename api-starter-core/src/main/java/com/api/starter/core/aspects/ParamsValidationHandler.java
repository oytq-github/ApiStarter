package com.api.starter.core.aspects;

import com.api.starter.core.annotations.NotEmpty;
import com.api.starter.core.annotations.ParamsCheck;
import com.api.starter.util.utils.ResponseEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

@Slf4j
@Aspect
@Service
@Order(1)
public class ParamsValidationHandler {

    //使用@ParamsCheck的参数数量不能大于8,如果参数数量较多,请考虑封装对象
    private static final int SLOT_LENGTH = 4;

    private static final String GETTER_METHOD_PRE = "get";

    @Pointcut()
    public void ParamsValidation() {
    }

    @Around("ParamsValidation()")
    public Object validation(ProceedingJoinPoint pjp)throws Throwable{

        Signature signature = pjp.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;

        Annotation[][] parameterAnnotations = methodSignature.getMethod().getParameterAnnotations();

        int indexes = 0;
        for (int index = parameterAnnotations.length - 1; index >= 0; index--) {
            for (int subIndex = 0; subIndex < parameterAnnotations[index].length; subIndex++) {
                if (parameterAnnotations[index][subIndex].annotationType().equals(ParamsCheck.class)) {
                    indexes = (indexes << SLOT_LENGTH) + index;
                }
            }
        }
        for (;indexes > 0;indexes = indexes >> SLOT_LENGTH) {
            Object params = pjp.getArgs()[indexes & 0b1111];
            if (params == null) {
                return ResponseEntity.FAIL("请求参数不允许为空");
            }
            Field[] declaredFields = params.getClass().getDeclaredFields();
            for (Field item : declaredFields) {
                NotEmpty annotation = item.getAnnotation(NotEmpty.class);
                if (null == annotation) {
                    continue;
                }
                String getter = GETTER_METHOD_PRE
                        .concat(item.getName().substring(0, 1).toUpperCase())
                        .concat(item.getName().substring(1));
                Method method = params.getClass().getMethod(getter);
                Object objectValue = method.invoke(params);
                if (objectValue == null || (objectValue instanceof String && StringUtils.isBlank((String) objectValue))) {
                    return ResponseEntity.FAIL(annotation.value());
                }
            }
        }

        return pjp.proceed();
    }
}
