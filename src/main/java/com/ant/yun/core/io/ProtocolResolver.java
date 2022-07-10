package com.ant.yun.core.io;

import com.ant.yun.lang.Nullable;

/**
 * @author jack_yun
 * @version 1.0
 * @description: TODO
 * @date 2022/7/9 11:06
 */
@FunctionalInterface
public interface ProtocolResolver {
    @Nullable
    Resource resolve(String var1, ResourceLoader var2);
}
