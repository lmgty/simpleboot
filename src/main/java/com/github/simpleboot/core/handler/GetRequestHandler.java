package com.github.simpleboot.core.handler;

import annotation.RequestParam;
import com.github.simpleboot.common.utils.ObjectUtils;
import com.github.simpleboot.common.utils.ReflectionUtils;
import com.github.simpleboot.common.utils.UrlUtils;
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
        Map<String, String> queryParams = UrlUtils.getQueryParams(queryDecoder.parameters());
        // 获取请求路径，如 "/user"
        String url = queryDecoder.path();
        // 获取目标方法
        Method targetMethod = Router.getMappings.get(url);
        if (targetMethod == null) {
            return null;
        }
        log.info("url -> target method [{}]", targetMethod.getName());
        Parameter[] targetMethodParameters = targetMethod.getParameters();
        List<Object> targetParams = new ArrayList<>();
        for (Parameter parameter : targetMethodParameters) {
            RequestParam requestParam = parameter.getDeclaredAnnotation(RequestParam.class);
            if (requestParam != null) {
                String requestParameter = requestParam.value();
                String requestParameterValue = queryParams.get(requestParameter);
                if (requestParameterValue == null) {
                    throw new IllegalArgumentException("指定参数" + requestParameter + "不能为空");
                }
                Object param = ObjectUtils.convert(parameter.getType(), requestParameterValue);
                targetParams.add(param);
            }
        }

        return ReflectionUtils.executeMethod(targetMethod,targetParams.toArray());
    }

}
