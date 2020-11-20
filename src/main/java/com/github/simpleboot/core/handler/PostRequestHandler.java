package com.github.simpleboot.core.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @author LiuYe
 * @data 2020/11/18
 */
@Slf4j
public class PostRequestHandler implements RequestHandler {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Object handle(FullHttpRequest fullHttpRequest) {
        String typeStr = fullHttpRequest.headers().get("Content-Type");
        String[] list = typeStr.split(";");
        String contentType = list[0];
        String jsonStr = null;
        if ("application/json".equals(contentType)) {
            jsonStr = fullHttpRequest.content().toString(CharsetUtil.UTF_8);
        }
        return jsonStr;
    }
}
