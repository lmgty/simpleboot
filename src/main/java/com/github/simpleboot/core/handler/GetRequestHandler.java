package com.github.simpleboot.core.handler;

import annotation.RequestParam;
import com.github.simpleboot.common.utils.HttpRequestUtil;
import com.github.simpleboot.common.utils.ObjectUtil;
import com.github.simpleboot.common.utils.ReflectionUtil;
import com.github.simpleboot.core.Router;
import io.netty.handler.codec.http.FullHttpRequest;
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
        // /user?name=Bo&g=44&address=北京
        String requestUri = fullHttpRequest.uri();
        Map<String, String> queryParams = HttpRequestUtil.getQueryParams(requestUri);
        // 获取请求路径，如 "/user"
        String requestPath = HttpRequestUtil.getRequestPath(requestUri);
        // 获取目标方法
        Method targetMethod = Router.getMappings.get(requestPath);
        if (targetMethod == null) {
            return null;
        }
        log.info("requestUri -> target method [{}]", targetMethod.getName());
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
                Object param = ObjectUtil.convert(parameter.getType(), requestParameterValue);
                targetParams.add(param);
            }
        }

        return ReflectionUtil.executeMethod(targetMethod,targetParams.toArray());
    }

}
