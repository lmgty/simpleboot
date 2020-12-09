package com.github.simpleboot.core;

import com.github.simpleboot.annotation.GetMapping;
import com.github.simpleboot.annotation.PostMapping;
import com.github.simpleboot.annotation.RestController;
import com.github.simpleboot.common.utils.UrlUtil;
import com.github.simpleboot.core.entity.MethodDetail;
import com.github.simpleboot.core.scanners.AnnotatedClassScanner;
import io.netty.handler.codec.http.HttpMethod;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * @author LiuYe
 * @data 2020/11/20
 */
public class ApplicationContext {
    private static final ApplicationContext INSTANCE = new ApplicationContext();

    /**
     * formatUrl -> method
     * "^/user/[\u4e00-\u9fa5_a-zA-Z0-9]+/?$" -> method
     */
    private static final Map<String, Method> GET_MAPPINGS = new HashMap<>();
    private static final Map<String, Method> POST_MAPPINGS = new HashMap<>();
    /**
     * formatUrl -> url
     * "^/user/[\u4e00-\u9fa5_a-zA-Z0-9]+/?$" -> "/user/{name}"
     */
    private static final Map<String, String> GET_URL_MAPPINGS = new HashMap<>();
    private static final Map<String, String> POST_URL_MAPPINGS = new HashMap<>();
    /**
     * formatUrl -> methodDetail
     * "^/user/[\u4e00-\u9fa5_a-zA-Z0-9]+/?$" -> methodDetail
     */
    private static final Map<String, MethodDetail> GET_METHOD_MAPPINGS = new HashMap<>();
    private static final Map<String, MethodDetail> POST_METHOD_MAPPINGS = new HashMap<>();


    private ApplicationContext() {
    }

    public static ApplicationContext getInstance() {
        return INSTANCE;
    }

    public void loadRoutes(String packageName) {
        Set<Class<?>> classes = AnnotatedClassScanner.scan(packageName, RestController.class);
        for (Class<?> aClass : classes) {
            RestController restController = aClass.getAnnotation(RestController.class);
            if (restController != null) {
                Method[] methods = aClass.getMethods();
                String baseUrl = restController.value();

                for (Method method : methods) {
                    if (method.isAnnotationPresent(GetMapping.class)) {
                        GetMapping getMapping = method.getAnnotation(GetMapping.class);
                        String url = baseUrl + getMapping.value();
                        String formattedUrl = UrlUtil.formatUrl(url);
                        GET_MAPPINGS.put(formattedUrl, method);
                        GET_URL_MAPPINGS.put(formattedUrl, url);
                    }
                    if (method.isAnnotationPresent(PostMapping.class)) {
                        PostMapping postMapping = method.getAnnotation(PostMapping.class);
                        if (postMapping != null) {
                            String url = baseUrl + postMapping.value();
                            String formattedUrl = UrlUtil.formatUrl(url);
                            POST_MAPPINGS.put(formattedUrl, method);
                            POST_URL_MAPPINGS.put(formattedUrl, url);
                        }
                    }
                }
            }
        }
    }

    public MethodDetail getMethodDetail(String requestPath, HttpMethod httpMethod) {
        MethodDetail methodDetail = null;
        if (httpMethod == HttpMethod.GET) {
            methodDetail = handle(requestPath, GET_MAPPINGS, GET_URL_MAPPINGS, GET_METHOD_MAPPINGS);
        }

        if (httpMethod == HttpMethod.POST) {
            methodDetail = handle(requestPath, POST_MAPPINGS, POST_URL_MAPPINGS, POST_METHOD_MAPPINGS);
        }
        return methodDetail;
    }

    private MethodDetail handle(String requestPath, Map<String, Method> getMappings, Map<String, String> getUrlMappings, Map<String, MethodDetail> getMethodMappings) {
        MethodDetail methodDetail = new MethodDetail();

        // getMappings : "^/user/[\u4e00-\u9fa5_a-zA-Z0-9]+/?$" -> method
        getMappings.forEach((key, value) -> {
            Pattern pattern = Pattern.compile(key);
            boolean find = pattern.matcher(requestPath).find();
            if (find) {
                methodDetail.setMethod(value);
                // getUrlMappings : "^/user/[\u4e00-\u9fa5_a-zA-Z0-9]+/?$" -> "/user/{name}"
                String url = getUrlMappings.get(key);
                // urlParameterMappings : name -> 盖伦
                Map<String, String> urlParameterMappings = UrlUtil.getUrlParameterMappings(requestPath, url);
                methodDetail.setUrlParameterMappings(urlParameterMappings);
                // getMethodMappings : "^/user/[\u4e00-\u9fa5_a-zA-Z0-9]+/?$" -> methodDetail
                getMethodMappings.put(key, methodDetail);
            }
        });

        return methodDetail;
    }
}

