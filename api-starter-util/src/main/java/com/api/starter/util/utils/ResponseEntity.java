package com.api.starter.util.utils;

import com.github.pagehelper.PageInfo;
import lombok.Data;
import lombok.NonNull;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;


@Data
public class ResponseEntity<T>  {

    private Integer code = 20000;

    private String message;

    private T data;

    /**
     * 成功响应
     * @return {@link ResponseEntity<>}
     */
    public static ResponseEntity SUCCESS() {
        return new ResponseEntity<>();
    }

    /**
     * 判断空值与boolean值的成功响应
     * @return {@link ResponseEntity<>}
     */
    public static ResponseEntity CONDITION(Boolean condition) {
        if (null == condition || !condition) {
            return ResponseEntity.FAIL();
        }
        return new ResponseEntity<>();
    }

    /**
     * 判断空值与boolean值的成功响应
     * @return {@link ResponseEntity<>}
     */
    public static ResponseEntity CONDITION(Boolean condition, String failDefaultMessage) {
        if (null == condition || !condition) {
            if (StringUtils.isEmpty(failDefaultMessage)) {
                return ResponseEntity.FAIL();
            }
            return ResponseEntity.FAIL(failDefaultMessage);
        }
        return new ResponseEntity<>();
    }

    /**
     * 成功响应
     * @param body 成功数据 {@link T}
     * @return {@link ResponseEntity<>}
     */
    public static <T> ResponseEntity SUCCESS(@NonNull T body) {
        ResponseEntity<T> responseEntity = new ResponseEntity<>();
        responseEntity.setData(body);
        return responseEntity;
    }

    /**
     * 带分页信息的成功响应
     * @param list 成功数据 {@link T}
     * @return {@link ResponseEntity<>}
     */
    public static <T> ResponseEntity PAGE_SUCCESS(List<T> list) {
        ResponseEntity<PageInfo> responseEntity = new ResponseEntity<>();
        if (CollectionUtils.isEmpty(list)) {
            list = new ArrayList<>();
        }
        PageInfo<T> tPageInfo = new PageInfo<>(list);
        responseEntity.setData(tPageInfo);
        return responseEntity;
    }

    /**
     * 通用业务失败响应
     * @param message 错误信息
     * @return {@link ResponseEntity<>}
     */
    public static ResponseEntity FAIL(@NonNull String message) {
        ResponseEntity responseEntity = new ResponseEntity<>();
        responseEntity.setMessage(message);
        responseEntity.setCode(30000);
        return responseEntity;
    }

    /**
     * 通用业务失败响应
     * @return {@link ResponseEntity<>}
     */
    public static ResponseEntity FAIL() {
        ResponseEntity responseEntity = new ResponseEntity<>();
        responseEntity.setCode(30000);
        return responseEntity;
    }

    /**
     * 失败响应
     * @return {@link ResponseEntity<>}
     */
    public static ResponseEntity FAIL(@NonNull Integer code) {
        ResponseEntity responseEntity = new ResponseEntity<>();
        responseEntity.setCode(code);
        return responseEntity;
    }

    /**
     * 失败响应
     * @return {@link ResponseEntity<>}
     */
    public static ResponseEntity FAIL(@NonNull String message,@NonNull Integer code) {
        ResponseEntity responseEntity = new ResponseEntity<>();
        responseEntity.setMessage(message);
        responseEntity.setCode(code);
        return responseEntity;
    }
}
