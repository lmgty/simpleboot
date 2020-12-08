package com.github.simpleboot.core.handler;

import annotation.RequestBody;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.simpleboot.common.utils.HttpRequestUtil;
import com.github.simpleboot.common.utils.ReflectionUtil;
import com.github.simpleboot.core.Router;
import io.netty.handler.codec.http.FullHttpRequest;
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
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Object handle(FullHttpRequest fullHttpRequest) {
        String requestUri = fullHttpRequest.uri();
        String requestPath = HttpRequestUtil.getRequestPath(requestUri);
        Method targetMethod = Router.postMappings.get(requestPath);
        if (targetMethod == null) {
            return null;
        }
        String contentType = HttpRequestUtil.getContentType(fullHttpRequest.headers());

        List<Object> targetMethodParams = new ArrayList<>();
        if (APPLICATION_JSON.equals(contentType)) {
            String json = fullHttpRequest.content().toString(CharsetUtil.UTF_8);
            Parameter[] targetMethodParameters = targetMethod.getParameters();
            for (Parameter parameter : targetMethodParameters) {
                RequestBody requestBody = parameter.getDeclaredAnnotation(RequestBody.class);
                if (requestBody != null) {
                    try {
                        Object obj = objectMapper.readValue(json, parameter.getType());
                        targetMethodParams.add(obj);
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                }
            }
        } else {
            throw new IllegalArgumentException("only receive application/json type data");
        }

        return ReflectionUtil.executeMethod(targetMethod, targetMethodParams.toArray());
    }
}
