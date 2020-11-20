package annotation;

import java.lang.annotation.*;

/**
 * @author LiuYe
 * @data 2020/11/20
 */
@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface GetMapping {
    String value() default "";

}
