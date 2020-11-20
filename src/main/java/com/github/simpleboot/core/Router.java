package com.github.simpleboot.core;

import annotation.GetMapping;
import annotation.PostMapping;
import annotation.RestController;
import com.github.simpleboot.core.scanners.AnnotatedClassScanner;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author LiuYe
 * @data 2020/11/20
 */
public class Router {

    public static Map<String, Method> getMappings = new HashMap<>();
    public static Map<String, Method> postMappings = new HashMap<>();
    public static Map<String, Class<?>> classMappings = new HashMap<>();

    public void loadRoutes(String packageName) {
        Set<Class<?>> classes = AnnotatedClassScanner.scan(packageName, RestController.class);
        for (Class<?> aClass : classes) {
            RestController restController = aClass.getAnnotation(RestController.class);
            if (restController != null) {
                Method[] methods = aClass.getMethods();
                String baseUrl = restController.value();
                traverseMethods(baseUrl, methods);
                classMappings.put(baseUrl, aClass);
            }
        }
    }

    private void traverseMethods(String baseUrl, Method[] methods) {
        for (Method method : methods) {
            if (method.isAnnotationPresent(GetMapping.class)) {
                GetMapping getMapping = method.getAnnotation(GetMapping.class);
                if (getMapping != null){
                    getMappings.put(baseUrl+getMapping.value(), method);
                }
            }
            if (method.isAnnotationPresent(PostMapping.class)) {
                PostMapping postMapping = method.getAnnotation(PostMapping.class);
                if (postMapping != null){
                    postMappings.put(baseUrl + postMapping.value(), method);
                }
            }
        }
    }
}

