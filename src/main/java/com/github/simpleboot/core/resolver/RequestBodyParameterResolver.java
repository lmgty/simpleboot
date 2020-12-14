package com.github.simpleboot.core.resolver;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.simpleboot.annotation.RequestBody;
import com.github.simpleboot.core.entity.MethodDetail;

import java.lang.reflect.Parameter;

/**
 * @author LiuYe
 * @data 2020/12/9
 */
public class RequestBodyParameterResolver implements ParameterResolver {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Override
    public Object resolve(MethodDetail methodDetail, Parameter parameter) {
        Object param = null;
        RequestBody requestBody = parameter.getDeclaredAnnotation(RequestBody.class);
        if (requestBody != null) {
            try {
                param = OBJECT_MAPPER.readValue(methodDetail.getJson(), parameter.getType());
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        return param;
    }
}
