package com.ant.yun.core;

import com.ant.yun.lang.Nullable;

/**
 * @author jack_yun
 * @version 1.0
 * @description: TODO
 * @date 2022/7/9 13:45
 */
public interface AttributeAccessor {
    void setAttribute(String var1, @Nullable Object var2);

    @Nullable
    Object getAttribute(String var1);

    @Nullable
    Object removeAttribute(String var1);

    boolean hasAttribute(String var1);

    String[] attributeNames();
}
