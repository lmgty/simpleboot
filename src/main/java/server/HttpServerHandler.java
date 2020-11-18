package server;

import handler.GetRequestHandler;
import handler.PostRequestHandler;
import handler.RequestHandler;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import lombok.extern.slf4j.Slf4j;
import serialize.JacksonSerialize;

import java.util.HashMap;
import java.util.Map;

import static io.netty.handler.codec.http.HttpHeaderNames.*;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

/**
 * @author LiuYe
 * @data 2020/11/17
 */
@Slf4j
public class HttpServerHandler extends SimpleChannelInboundHandler<FullHttpRequest> {
    private static final String FAVICON_ICO = "/favicon.ico";
    private final Map<HttpMethod, RequestHandler> requestHandlers;

    public HttpServerHandler() {
        requestHandlers = new HashMap<>();
        requestHandlers.put(HttpMethod.GET, new GetRequestHandler());
        requestHandlers.put(HttpMethod.POST, new PostRequestHandler());
    }

    /**
     * 与 FullHttpRequest 匹配上的信息，走这个方法进行处理
     *
     * @param ctx             上下文
     * @param fullHttpRequest HTTP请求
     * @throws Exception 异常
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest fullHttpRequest) throws Exception {
        log.info("Handle http request:{}", fullHttpRequest);
        String uri = fullHttpRequest.uri();
        if (uri.equals(FAVICON_ICO)) {
            return;
        }
        RequestHandler requestHandler = requestHandlers.get(fullHttpRequest.method());
        Object result = requestHandler.handle(fullHttpRequest);
        FullHttpResponse response = buildHttpResponse(result);
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

    private FullHttpResponse buildHttpResponse(Object result) {
        JacksonSerialize jacksonSerialize = new JacksonSerialize();
        byte[] content = jacksonSerialize.serialize(result);
        FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, OK,
                Unpooled.wrappedBuffer(content));
        response.headers().set(CONTENT_TYPE, "application/json");
        response.headers().setInt(CONTENT_LENGTH, response.content().readableBytes());
        return response;
    }
}
