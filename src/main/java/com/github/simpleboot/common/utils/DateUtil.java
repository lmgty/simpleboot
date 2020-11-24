package com.github.simpleboot.common.utils;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

/**
 * @author LiuYe
 * @data 2020/11/24
 */
public class DateUtil {
    FormatStyle dateTimeStyle;
    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT)
                    .withLocale(Locale.CHINA)
                    .withZone(ZoneId.systemDefault());

    public static String now() {
        return FORMATTER.format(Instant.now());
    }
}
