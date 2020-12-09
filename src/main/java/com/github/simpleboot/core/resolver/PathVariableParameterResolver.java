package com.github.simpleboot.core.resolver;

import com.github.simpleboot.annotation.PathVariable;
import com.github.simpleboot.common.utils.ObjectUtil;
import com.github.simpleboot.core.entity.MethodDetail;

import java.lang.reflect.Parameter;

/**
 * @author LiuYe
 * @data 2020/12/9
 */
public class PathVariableParameterResolver implements ParameterResolver {
    @Override
    public Object resolve(MethodDetail methodDetail, Parameter parameter) {
        // 如果是 restful 风格的占位符
        PathVariable pathVariable = parameter.getDeclaredAnnotation(PathVariable.class);
        // 获取占位符名称
        String requestParameter = pathVariable.value();
        String requestParameterValue = methodDetail.getQueryParameterMappings().get(requestParameter);
        return ObjectUtil.convert(parameter.getType(),requestParameterValue);
    }
}
