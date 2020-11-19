package com.github.simpleboot.scanners;

import lombok.extern.slf4j.Slf4j;
import org.reflections.Reflections;
import org.reflections.scanners.TypeAnnotationsScanner;

import java.lang.annotation.Annotation;
import java.util.Set;

/**
 * @author LiuYe
 * @data 2020/11/19
 */
@Slf4j
public class AnnotatedClassScanner {
    public Set<Class<?>> scan(String packageName, Class<? extends Annotation> annotation) {
        Reflections reflections = new Reflections(packageName, new TypeAnnotationsScanner());
        Set<Class<?>> annotatedClass = reflections.getTypesAnnotatedWith(annotation, true);
        log.info("The number of class Annotated with  @RestController :[{}]", annotatedClass.size());
        return annotatedClass;
    }
}
