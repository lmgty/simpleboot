package com.github.simpleboot.core.handler;

import annotation.RequestParam;
import com.github.simpleboot.core.Router;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.QueryStringDecoder;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.HashMap;
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
        QueryStringDecoder queryDecoder = new QueryStringDecoder(fullHttpRequest.uri(), CharsetUtil.UTF_8);
        Map<String, String> queryParams = getQueryParams(queryDecoder);
        // 获取请求路径，如 "/user"
        String url = queryDecoder.path();
        // 获取目标方法
        Method targetMethod = Router.getMappings.get(url);
        if (targetMethod == null) {
            return null;
        }
        log.info("url -> target method [{}]", targetMethod.getName());
        Parameter[] targetMethodParameters = targetMethod.getParameters();
        List<String> params = new ArrayList<>();
        for (Parameter parameter : targetMethodParameters) {
            RequestParam requestParam = parameter.getDeclaredAnnotation(RequestParam.class);
            if (requestParam != null) {
                String requestParameter = requestParam.value();
                String paramsValue = queryParams.get(requestParameter);
                if (paramsValue == null) {
                    throw new IllegalArgumentException("指定参数" + requestParameter + "不能为空");
                }
                params.add(paramsValue);
            }
        }
        // 将 url 参数和目标方法的参数对应上
        Object result = null;
        try {
            // 获取声明此方法的对象
            Object targetObject = targetMethod.getDeclaringClass().newInstance();
            result = targetMethod.invoke(targetObject, params.toArray());
        } catch (IllegalAccessException | InvocationTargetException | InstantiationException e) {
            e.printStackTrace();
        }

        return result;
    }

    private Map<String, String> getQueryParams(QueryStringDecoder queryDecoder) {
        Map<String, List<String>> uriAttributes = queryDecoder.parameters();
        HashMap<String, String> queryParams = new HashMap<>();
        for (Map.Entry<String, List<String>> attr : uriAttributes.entrySet()) {
            for (String attrVal : attr.getValue()) {
                log.info(attr.getKey() + "=" + attrVal);
                queryParams.put(attr.getKey(), attrVal);
            }
        }
        return queryParams;
    }
}
