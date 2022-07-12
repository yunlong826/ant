package com.ant.yun.beans.factory;

import com.ant.yun.lang.Nullable;

/**
 * @author jack_yun
 * @version 1.0
 * @description: TODO
 * @date 2022/7/12 20:35
 */
public interface FactoryBean<T>{
    @Nullable
    T getObject() throws Exception;

    @Nullable
    Class<?> getObjectType();

    default boolean isSingleton() {
        return true;
    }
}
