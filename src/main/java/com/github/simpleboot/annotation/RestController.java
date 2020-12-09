package com.github.simpleboot.annotation;

import java.lang.annotation.*;

/**
 * @author LiuYe
 * @data 2020/11/19
 */

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RestController {
    String value() default "";
}
