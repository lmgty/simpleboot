package com.github.simpleboot.core.resolver;

import com.github.simpleboot.annotation.RequestParam;
import com.github.simpleboot.common.utils.ObjectUtil;
import com.github.simpleboot.core.entity.MethodDetail;

import java.lang.reflect.Parameter;

/**
 * @author LiuYe
 * @data 2020/12/9
 */
public class RequestParamParameterResolver implements ParameterResolver {
    @Override
    public Object resolve(MethodDetail methodDetail, Parameter parameter) {
        RequestParam requestParam = parameter.getDeclaredAnnotation(RequestParam.class);
        String requestParameter = requestParam.value();
        String requestParameterValue = methodDetail.getQueryParameterMappings().get(requestParameter);
        if (requestParameterValue == null) {
            throw new IllegalArgumentException("The specified parameter " + requestParameter + " can not be null!");
        }
        // convert the parameter to the specified type
        return ObjectUtil.convert(parameter.getType(), requestParameterValue);
    }
}
