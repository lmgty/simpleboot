package com.github.simpleboot.common.utils;

import java.beans.PropertyEditor;
import java.beans.PropertyEditorManager;

/**
 * @author LiuYe
 * @data 2020/11/23
 */
public class ObjectUtil {
    public static Object convert(Class<?> targetType, String text){
        PropertyEditor editor = PropertyEditorManager.findEditor(targetType);
        editor.setAsText(text);
        return editor.getValue();
    }
}
