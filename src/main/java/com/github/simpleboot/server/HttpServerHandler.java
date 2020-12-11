package com.github.simpleboot.server;

import com.github.simpleboot.common.utils.UrlUtil;
import com.github.simpleboot.core.handler.RequestHandlerFactory;
import com.github.simpleboot.core.handler.RequestHandler;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import lombok.extern.slf4j.Slf4j;

import static io.netty.handler.codec.http.HttpHeaderNames.*;

/**
 * @author LiuYe
 * @data 2020/11/17
 */
@Slf4j
public class HttpServerHandler extends SimpleChannelInboundHandler<FullHttpRequest> {
    private static final String FAVICON_ICO = "/favicon.ico";

    /**
     * 与 FullHttpRequest 匹配上的信息，走这个方法进行处理
     *
     * @param ctx             上下文
     * @param fullHttpRequest HTTP请求
     * @throws Exception 异常
     */
    @Override
    @SuppressWarnings("deprecation")
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest fullHttpRequest) throws Exception {
        String uri = fullHttpRequest.uri();
        if (uri.equals(FAVICON_ICO)) {
            return;
        }
        RequestHandler requestHandler = RequestHandlerFactory.create(fullHttpRequest.method());
        Object result;
        FullHttpResponse response;
        try {
            result = requestHandler.handle(fullHttpRequest);
            response = HttpResponse.ok(result);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            String requestPath = UrlUtil.getRequestPath(fullHttpRequest.uri());
            response = HttpResponse.internalServerError(requestPath, e.toString());
        }
        boolean keepAlive = HttpUtil.isKeepAlive(fullHttpRequest);
        if (!keepAlive) {
            ctx.write(response).addListener(ChannelFutureListener.CLOSE);
        } else {
            response.headers().set(CONNECTION, KEEP_ALIVE);
            ctx.write(response);
        }
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }
}
