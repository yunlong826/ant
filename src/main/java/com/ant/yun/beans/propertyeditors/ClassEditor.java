package com.ant.yun.beans.propertyeditors;

import com.ant.yun.lang.Nullable;
import com.ant.yun.util.ClassUtils;
import com.ant.yun.util.StringUtils;

import java.beans.PropertyEditorSupport;

/**
 * @author jack_yun
 * @version 1.0
 * @description: TODO
 * @date 2022/7/12 20:48
 */
public class ClassEditor extends PropertyEditorSupport {
    @Nullable
    private final ClassLoader classLoader;

    public ClassEditor() {
        this((ClassLoader)null);
    }

    public ClassEditor(@Nullable ClassLoader classLoader) {
        this.classLoader = classLoader != null ? classLoader : ClassUtils.getDefaultClassLoader();
    }

    public void setAsText(String text) throws IllegalArgumentException {
        if (StringUtils.hasText(text)) {
            this.setValue(ClassUtils.resolveClassName(text.trim(), this.classLoader));
        } else {
            this.setValue((Object)null);
        }

    }

    public String getAsText() {
        Class<?> clazz = (Class)this.getValue();
        return clazz != null ? ClassUtils.getQualifiedName(clazz) : "";
    }
}
