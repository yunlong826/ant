package com.ant.yun.beans;

import com.ant.yun.lang.Nullable;

import java.lang.reflect.Field;

/**
 * @author jack_yun
 * @version 1.0
 * @description: TODO
 * @date 2022/7/12 21:11
 */
public interface TypeConverter {
    @Nullable
    <T> T convertIfNecessary(@Nullable Object var1, @Nullable Class<T> var2) throws TypeMismatchException;

    @Nullable
    <T> T convertIfNecessary(@Nullable Object var1, @Nullable Class<T> var2, @Nullable MethodParameter var3) throws TypeMismatchException;

    @Nullable
    <T> T convertIfNecessary(@Nullable Object var1, @Nullable Class<T> var2, @Nullable Field var3) throws TypeMismatchException;

    @Nullable
    default <T> T convertIfNecessary(@Nullable Object value, @Nullable Class<T> requiredType, @Nullable TypeDescriptor typeDescriptor) throws TypeMismatchException {
        throw new UnsupportedOperationException("TypeDescriptor resolution not supported");
    }
}
