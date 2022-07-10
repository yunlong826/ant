package com.ant.yun.core.convert.converter;

import com.ant.yun.lang.Nullable;

/**
 * @author jack_yun
 * @version 1.0
 * @description: TODO
 * @date 2022/7/9 14:17
 */
@FunctionalInterface
public interface Converter<S, T> {
    @Nullable
    T convert(S var1);
}
