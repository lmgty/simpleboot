package com.github.simpleboot.core.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * @author LiuYe
 * @data 2020/12/8
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MethodDetail {
    private Method method;
    /**
     * 后端 value -> 前端 url
     *       name -> 盖伦
     */
    private Map<String, String> urlParameterMappings;
    /**
     * 请求参数
     * name -> Tom
     * age -> 12
     */
    private Map<String, String> queryParameterMappings;
    private String json;
}
