package com.ant.yun.util;

import com.ant.yun.lang.Nullable;

/**
 * @author jack_yun
 * @version 1.0
 * @description: TODO
 * @date 2022/7/12 20:16
 */
@FunctionalInterface
public interface StringValueResolver {
    @Nullable
    String resolveStringValue(String var1);
}
