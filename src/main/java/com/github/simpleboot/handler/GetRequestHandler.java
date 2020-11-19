package com.github.simpleboot.handler;

import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.QueryStringDecoder;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

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
        QueryStringDecoder queryStringDecoder = new QueryStringDecoder(fullHttpRequest.uri(), CharsetUtil.UTF_8);
        Map<String, List<String>> uriAttributes = queryStringDecoder.parameters();
        for (Map.Entry<String, List<String>> attr : uriAttributes.entrySet()) {
            for (String attrVal : attr.getValue()) {
                log.info(attr.getKey() + "=" + attrVal);
            }
        }
        return null;
    }
}
