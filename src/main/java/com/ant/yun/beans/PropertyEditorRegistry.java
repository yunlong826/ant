package com.ant.yun.beans;

import com.ant.yun.lang.Nullable;

import java.beans.PropertyEditor;

/**
 * @author jack_yun
 * @version 1.0
 * @description: TODO
 * @date 2022/7/12 20:27
 */
public interface PropertyEditorRegistry {
    void registerCustomEditor(Class<?> var1, PropertyEditor var2);

    void registerCustomEditor(@Nullable Class<?> var1, @Nullable String var2, PropertyEditor var3);

    @Nullable
    PropertyEditor findCustomEditor(@Nullable Class<?> var1, @Nullable String var2);
}
