package com.github.simpleboot.core.handler;

import com.github.simpleboot.annotation.RequestBody;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.simpleboot.common.utils.UrlUtil;
import com.github.simpleboot.common.utils.ReflectionUtil;
import com.github.simpleboot.core.ApplicationContext;
import com.github.simpleboot.core.entity.MethodDetail;
import com.github.simpleboot.core.resolver.ParameterResolver;
import com.github.simpleboot.core.resolver.ParameterResolverFactory;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author LiuYe
 * @data 2020/11/18
 */
@Slf4j
public class PostRequestHandler implements RequestHandler {

    private static final String APPLICATION_JSON = "application/json";

    @Override
    public Object handle(FullHttpRequest fullHttpRequest) {
        String requestUri = fullHttpRequest.uri();
        String requestPath = UrlUtil.getRequestPath(requestUri);
        ApplicationContext applicationContext = ApplicationContext.getInstance();
        MethodDetail methodDetail = applicationContext.getMethodDetail(requestPath, HttpMethod.POST);
        if (methodDetail == null) {
            return null;
        }
        Method targetMethod = methodDetail.getMethod();
        String contentType = this.getContentType(fullHttpRequest.headers());

        List<Object> targetMethodParams = new ArrayList<>();
        if (APPLICATION_JSON.equals(contentType)) {
            String json = fullHttpRequest.content().toString(CharsetUtil.UTF_8);
            methodDetail.setJson(json);
            Parameter[] targetMethodParameters = targetMethod.getParameters();
            for (Parameter parameter : targetMethodParameters) {
                ParameterResolver parameterResolver = ParameterResolverFactory.get(parameter);
                if (parameterResolver != null) {
                    Object param = parameterResolver.resolve(methodDetail, parameter);
                    targetMethodParams.add(param);
                }
            }
        } else {
            throw new IllegalArgumentException("only receive application/json type data");
        }

        return ReflectionUtil.executeMethod(targetMethod, targetMethodParams.toArray());
    }

    private String getContentType(HttpHeaders headers) {
        String typeStr = headers.get("Content-type");
        String[] list = typeStr.split(";");
        return list[0];
    }
}
