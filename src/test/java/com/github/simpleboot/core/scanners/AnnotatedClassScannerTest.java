package com.github.simpleboot.core.scanners;

import com.github.simpleboot.annotation.RestController;
import org.junit.jupiter.api.Test;

import java.lang.annotation.Annotation;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author LiuYe
 * @data 2020/11/19
 */
public class AnnotatedClassScannerTest {

    @Test
    void should_scan_the_annotated_class() {
        Set<Class<?>> annotatedClasses = AnnotatedClassScanner.scan("com.github.simpleboot.core.scanners", RestController.class);
        assertEquals(1, annotatedClasses.size());
    }

    @Test
    void reflection_Test() {
        Class<?> so = HelloController.class;
        Annotation[] annotations = so.getAnnotations();
        assertEquals(1, annotations.length);
    }

}
