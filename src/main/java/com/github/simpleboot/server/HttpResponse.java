package com.github.simpleboot.server;

import com.github.simpleboot.exception.ErrorResponse;
import com.github.simpleboot.serialize.JacksonSerialize;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;

import static io.netty.handler.codec.http.HttpHeaderNames.CONTENT_LENGTH;
import static io.netty.handler.codec.http.HttpHeaderNames.CONTENT_TYPE;
import static io.netty.handler.codec.http.HttpResponseStatus.INTERNAL_SERVER_ERROR;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

/**
 * @author LiuYe
 * @data 2020/11/24
 */
public class HttpResponse {
    private static final JacksonSerialize JSON_SERIALIZE;
    static {
        JSON_SERIALIZE = new JacksonSerialize();
    }

    public static FullHttpResponse ok(Object result) {
        byte[] content = JSON_SERIALIZE.serialize(result);
        FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1,
                OK,
                Unpooled.wrappedBuffer(content));
        response.headers().set(CONTENT_TYPE, "application/json");
        response.headers().setInt(CONTENT_LENGTH, response.content().readableBytes());
        return response;
    }


    public static FullHttpResponse internalServerError(String url, String message) {
        ErrorResponse errorResponse = new ErrorResponse(
                INTERNAL_SERVER_ERROR.code(),
                INTERNAL_SERVER_ERROR.reasonPhrase(),
                message,
                url);
        byte[] content = JSON_SERIALIZE.serialize(errorResponse);
        FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1,
                INTERNAL_SERVER_ERROR,
                Unpooled.wrappedBuffer(content));
        response.headers().set(CONTENT_TYPE, "application/json");
        response.headers().setInt(CONTENT_LENGTH, response.content().readableBytes());
        return response;
    }
}
