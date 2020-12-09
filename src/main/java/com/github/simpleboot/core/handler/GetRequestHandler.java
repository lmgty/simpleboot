package com.github.simpleboot.core.handler;

import com.github.simpleboot.annotation.RequestParam;
import com.github.simpleboot.common.utils.UrlUtil;
import com.github.simpleboot.common.utils.ObjectUtil;
import com.github.simpleboot.common.utils.ReflectionUtil;
import com.github.simpleboot.core.ApplicationContext;
import com.github.simpleboot.core.entity.MethodDetail;
import com.github.simpleboot.core.resolver.ParameterResolver;
import com.github.simpleboot.core.resolver.ParameterResolverFactory;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpMethod;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author LiuYe
 * @data 2020/11/18
 */
@Slf4j
public class GetRequestHandler implements RequestHandler {
    @Override
    public Object handle(FullHttpRequest fullHttpRequest) {
        // /user?name=Bob&age=44&address=北京
        String requestUri = fullHttpRequest.uri();
        Map<String, String> queryParameterMappings = UrlUtil.getQueryParams(requestUri);
        // 获取请求路径，如 "/user"
        String requestPath = UrlUtil.getRequestPath(requestUri);
        // 获取目标方法
        ApplicationContext applicationContext = ApplicationContext.getInstance();
        MethodDetail methodDetail = applicationContext.getMethodDetail(requestPath, HttpMethod.GET);
        if (methodDetail == null) {
            return null;
        }
        methodDetail.setQueryParameterMappings(queryParameterMappings);
        Method targetMethod = methodDetail.getMethod();
        if (targetMethod == null) {
            return null;
        }

        log.info("requestUri -> target method [{}]", targetMethod.getName());
        Parameter[] targetMethodParameters = targetMethod.getParameters();
        List<Object> targetMethodParams = new ArrayList<>();
        for (Parameter parameter : targetMethodParameters) {
            ParameterResolver parameterResolver = ParameterResolverFactory.get(parameter);
            if (parameterResolver != null) {
                Object param = parameterResolver.resolve(methodDetail, parameter);
                targetMethodParams.add(param);
            }
        }

        return ReflectionUtil.executeMethod(targetMethod, targetMethodParams.toArray());
    }

}
