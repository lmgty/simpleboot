package handler;

import io.netty.handler.codec.http.FullHttpRequest;

/**
 * @author LiuYe
 * @data 2020/11/18
 */
public interface RequestHandler {

    /**
     * 处理请求
     * @param fullHttpRequest 请求
     * @return 处理结果
     */
    Object handle(FullHttpRequest fullHttpRequest);
}
