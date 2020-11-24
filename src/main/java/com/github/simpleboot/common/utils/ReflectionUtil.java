package com.github.simpleboot.common.utils;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
/**
 * @author LiuYe
 * @data 2020/11/23
 */
@Slf4j
public class ReflectionUtil {
    public static Object executeMethod(Method method, Object... args){
        // 将 url 参数和目标方法的参数对应上
        Object result = null;
        try {
            // 获取声明此方法的对象
            Object targetObject = method.getDeclaringClass().newInstance();
            result = method.invoke(targetObject, args);
            log.info("invoke target method successfully ,result is: [{}]", result.toString());
        } catch (IllegalAccessException | InvocationTargetException | InstantiationException e) {
            e.printStackTrace();
        }

        return result;
    }
}
