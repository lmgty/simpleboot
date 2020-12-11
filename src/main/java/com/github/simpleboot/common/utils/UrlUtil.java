package com.github.simpleboot.common.utils;

import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.QueryStringDecoder;
import io.netty.util.CharsetUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author LiuYe
 * @data 2020/11/23
 */
public class UrlUtil {
    public static Map<String, String> getQueryParams(String uri) {
        QueryStringDecoder queryDecoder = new QueryStringDecoder(uri, CharsetUtil.UTF_8);
        Map<String, List<String>> parameters = queryDecoder.parameters();
        HashMap<String, String> queryParams = new HashMap<>(16);
        for (Map.Entry<String, List<String>> attr : parameters.entrySet()) {
            for (String attrVal : attr.getValue()) {
                queryParams.put(attr.getKey(), attrVal);
            }
        }
        return queryParams;
    }

    public static String getRequestPath(String uri) {
        QueryStringDecoder queryDecoder = new QueryStringDecoder(uri, CharsetUtil.UTF_8);
        return queryDecoder.path();
    }

    /**
     * format the url
     * for example : "/user/{name}" -> "^/user/[\u4e00-\u9fa5_a-zA-Z0-9]+/?$"
     */
    public static String formatUrl(String url) {
        // replace {xxx} placeholders with regular expressions matching Chinese, English letters and numbers, and underscores
        // 字符串中 "\\" 表示 "\",正则表达式中 "\\" 表示 "\"
        // 所以在用字符串写正则表达式时 "\\\\" 表示正则中的 "\"
        String originPattern = url.replaceAll("\\{\\w+}", "[\\\\u4e00-\\\\u9fa5_a-zA-Z0-9]+");
        String pattern = "^" + originPattern + "/?$";
        // 去重 "/"
        return pattern.replaceAll("/+", "/");
    }

    /**
     * for example :
     * if requestPath="/user/盖伦"  url="/user/{name}"
     * this method will return:
     */
    public static Map<String, String> getUrlParameterMappings(String requestPath, String url) {
        String[] requestParams = requestPath.split("/");
        String[] urlParams = url.split("/");
        Map<String, String> urlParameterMappings = new HashMap<>(16);
        for (int i = 1; i < urlParams.length; i++) {
            String urlParam = urlParams[i].replace("{", "")
                    .replace("}", "");
            // name -> 盖伦
            urlParameterMappings.put(urlParam, requestParams[i]);
        }
        return urlParameterMappings;
    }
}
