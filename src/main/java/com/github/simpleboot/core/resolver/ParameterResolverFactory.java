package com.github.simpleboot.core.resolver;

import com.github.simpleboot.annotation.PathVariable;
import com.github.simpleboot.annotation.RequestBody;
import com.github.simpleboot.annotation.RequestParam;

import java.lang.reflect.Parameter;

/**
 * @author LiuYe
 * @data 2020/12/9
 */
public class ParameterResolverFactory {
    public static ParameterResolver get(Parameter parameter) {
        if (parameter.isAnnotationPresent(RequestParam.class)) {
            return new RequestParamParameterResolver();
        }
        if (parameter.isAnnotationPresent(PathVariable.class)) {
            return new PathVariableParameterResolver();
        }
        if (parameter.isAnnotationPresent(RequestBody.class)) {
            return new RequestBodyParameterResolver();
        }
        return null;
    }
}
