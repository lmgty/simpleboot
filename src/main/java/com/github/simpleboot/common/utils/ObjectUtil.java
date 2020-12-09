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
        // void setAsText(String text)：用一个字符串去更新属性的内部值，这个字符串一般从外部属性编辑器传入；
        editor.setAsText(text);
        // Object getValue()：返回属性的当前值。基本类型被封装成对应的包装类实例；
        return editor.getValue();
    }
}
