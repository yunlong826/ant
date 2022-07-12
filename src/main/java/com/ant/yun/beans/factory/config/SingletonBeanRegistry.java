package com.ant.yun.beans.factory.config;

import com.ant.yun.lang.Nullable;

/**
 * @author jack_yun
 * @version 1.0
 * @description: TODO
 * @date 2022/7/12 20:17
 */
public interface SingletonBeanRegistry {
    void registerSingleton(String var1, Object var2);

    @Nullable
    Object getSingleton(String var1);

    boolean containsSingleton(String var1);

    String[] getSingletonNames();

    int getSingletonCount();

    Object getSingletonMutex();
}
