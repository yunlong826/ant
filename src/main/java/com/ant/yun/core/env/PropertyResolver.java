package com.ant.yun.core.env;

import com.ant.yun.lang.Nullable;

/**
 * @author jack_yun
 * @version 1.0
 * @description: TODO
 * @date 2022/7/9 14:05
 */
public interface PropertyResolver {
    boolean containsProperty(String var1);

    @Nullable
    String getProperty(String var1);

    String getProperty(String var1, String var2);

    @Nullable
    <T> T getProperty(String var1, Class<T> var2);

    <T> T getProperty(String var1, Class<T> var2, T var3);

    String getRequiredProperty(String var1) throws IllegalStateException;

    <T> T getRequiredProperty(String var1, Class<T> var2) throws IllegalStateException;

    String resolvePlaceholders(String var1);

    String resolveRequiredPlaceholders(String var1) throws IllegalArgumentException;
}
