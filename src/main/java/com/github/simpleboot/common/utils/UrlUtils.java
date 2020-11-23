package com.github.simpleboot.common.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author LiuYe
 * @data 2020/11/23
 */
public class UrlUtils {
    public static Map<String, String> getQueryParams(Map<String, List<String>> uriAttributes) {
        HashMap<String, String> queryParams = new HashMap<>(16);
        for (Map.Entry<String, List<String>> attr : uriAttributes.entrySet()) {
            for (String attrVal : attr.getValue()) {
                queryParams.put(attr.getKey(), attrVal);
            }
        }
        return queryParams;
    }
}
