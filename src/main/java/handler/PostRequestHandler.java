package handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

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
