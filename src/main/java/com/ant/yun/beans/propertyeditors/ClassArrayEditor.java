package com.ant.yun.beans.propertyeditors;

import com.ant.yun.lang.Nullable;
import com.ant.yun.util.ClassUtils;
import com.ant.yun.util.ObjectUtils;
import com.ant.yun.util.StringUtils;

import java.beans.PropertyEditorSupport;

/**
 * @author jack_yun
 * @version 1.0
 * @description: TODO
 * @date 2022/7/12 20:49
 */
public class ClassArrayEditor extends PropertyEditorSupport {
    @Nullable
    private final ClassLoader classLoader;

    public ClassArrayEditor() {
        this((ClassLoader)null);
    }

    public ClassArrayEditor(@Nullable ClassLoader classLoader) {
        this.classLoader = classLoader != null ? classLoader : ClassUtils.getDefaultClassLoader();
    }

    public void setAsText(String text) throws IllegalArgumentException {
        if (StringUtils.hasText(text)) {
            String[] classNames = StringUtils.commaDelimitedListToStringArray(text);
            Class<?>[] classes = new Class[classNames.length];

            for(int i = 0; i < classNames.length; ++i) {
                String className = classNames[i].trim();
                classes[i] = ClassUtils.resolveClassName(className, this.classLoader);
            }

            this.setValue(classes);
        } else {
            this.setValue((Object)null);
        }

    }

    public String getAsText() {
        Class<?>[] classes = (Class[])((Class[])this.getValue());
        if (ObjectUtils.isEmpty(classes)) {
            return "";
        } else {
            StringBuilder sb = new StringBuilder();

            for(int i = 0; i < classes.length; ++i) {
                if (i > 0) {
                    sb.append(",");
                }

                sb.append(ClassUtils.getQualifiedName(classes[i]));
            }

            return sb.toString();
        }
    }
}
