package com.ant.yun.beans.factory.config;

import com.ant.yun.beans.factory.ObjectFactory;
import com.ant.yun.lang.Nullable;

/**
 * @author jack_yun
 * @version 1.0
 * @description: TODO
 * @date 2022/7/12 20:26
 */
public interface Scope {
    Object get(String var1, ObjectFactory<?> var2);

    @Nullable
    Object remove(String var1);

    void registerDestructionCallback(String var1, Runnable var2);

    @Nullable
    Object resolveContextualObject(String var1);

    @Nullable
    String getConversationId();
}
