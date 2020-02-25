package com.api.starter.api.controller;

import com.alibaba.fastjson.JSON;
import com.api.starter.core.utils.SpringContextHolder;
import com.api.starter.util.constants.Constant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * API接口,通过@PathVariable注解获取请求模块,请求方法,
 * 通过SpringContextHolder获取模块对象
 * 根据methodName过滤出请求目标方法
 * 通过method对象获取参数类型,并序列化参数
 * 通过反射执行请求方法
 * 返回执行结果
 */

@Slf4j
@RestController
@RequestMapping(Constant.API_MAPPER)
public class AdapterController {

    @RequestMapping(value = Constant.METHOD_MAPPER, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Object invokeApi(@PathVariable String serviceName, @PathVariable String methodName, @RequestBody String body) throws Exception {

        String beanName = serviceName.concat(Constant.SERVICE_SUF);
        Object bean = SpringContextHolder.getBean(beanName);

        List<Method> methods = Arrays.stream(bean.getClass().getDeclaredMethods()).filter(item -> item.getName().equals(methodName)).collect(Collectors.toList());
        Method method = methods.get(0);

        Class<?>[] parameterTypes = method.getParameterTypes();

        Object parameter = JSON.parseObject(body, parameterTypes[0]);

        return method.invoke(bean, parameter);
    }
}
