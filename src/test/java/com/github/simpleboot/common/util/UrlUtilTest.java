package com.github.simpleboot.common.util;

import com.github.simpleboot.common.utils.UrlUtil;
import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author LiuYe
 * @data 2020/12/9
 */
public class UrlUtilTest {
    @Test
    void should_format_url_successful() {
        String url = "/user/{name}";
        String formatUrl = UrlUtil.formatUrl(url);
        assertEquals("^/user/[\\u4e00-\\u9fa5_a-zA-Z0-9]+/?$", formatUrl);

        Pattern pattern = Pattern.compile(formatUrl);
        boolean a = pattern.matcher("/user/盖伦/1232").find();
        assertFalse(a);
        boolean b = pattern.matcher("/user/盖伦").find();
        assertTrue(b);
    }
}
