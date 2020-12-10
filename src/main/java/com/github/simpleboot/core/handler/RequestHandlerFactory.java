package com.github.simpleboot.core.handler;


import io.netty.handler.codec.http.HttpMethod;

import java.util.HashMap;
import java.util.Map;

/**
 * @author LiuYe
 * @data 2020/11/19
 */
public class RequestHandlerFactory {
    private static final Map<HttpMethod, RequestHandler> requestHandlers = new HashMap<>();

    static {
        requestHandlers.put(HttpMethod.GET, new GetRequestHandler());
        requestHandlers.put(HttpMethod.POST, new PostRequestHandler());
    }

    public static RequestHandler create(HttpMethod httpMethod){
        return requestHandlers.get(httpMethod);
    }
}