package com.ant.yun.core.convert;

import com.ant.yun.lang.Nullable;

/**
 * @author jack_yun
 * @version 1.0
 * @description: TODO
 * @date 2022/7/9 14:15
 */
public interface ConversionService {
    boolean canConvert(@Nullable Class<?> var1, Class<?> var2);

    boolean canConvert(@Nullable TypeDescriptor var1, TypeDescriptor var2);

    @Nullable
    <T> T convert(@Nullable Object var1, Class<T> var2);

    @Nullable
    Object convert(@Nullable Object var1, @Nullable TypeDescriptor var2, TypeDescriptor var3);
}
