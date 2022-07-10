package com.ant.yun.beans.factory.xml;

import com.ant.yun.lang.Nullable;

/**
 * @author jack_yun
 * @version 1.0
 * @description: TODO
 * @date 2022/7/9 15:42
 */
@FunctionalInterface
public interface NamespaceHandlerResolver {
    @Nullable
    NamespaceHandler resolve(String var1);
}
