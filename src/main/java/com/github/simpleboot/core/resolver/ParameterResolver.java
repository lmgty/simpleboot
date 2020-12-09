package com.github.simpleboot.core.resolver;

import com.github.simpleboot.core.entity.MethodDetail;

import java.lang.reflect.Parameter;

/**
 * @author LiuYe
 * @data 2020/12/8
 */
public interface ParameterResolver {

    Object resolve(MethodDetail methodDetail, Parameter parameter);
}
