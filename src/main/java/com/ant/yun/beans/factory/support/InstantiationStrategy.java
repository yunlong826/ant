package com.ant.yun.beans.factory.support;

import com.ant.yun.beans.BeansException;
import com.ant.yun.beans.factory.BeanFactory;
import com.ant.yun.lang.Nullable;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * @author jack_yun
 * @version 1.0
 * @description: TODO
 * @date 2022/7/13 21:49
 */
public interface InstantiationStrategy {
    Object instantiate(RootBeanDefinition var1, @Nullable String var2, BeanFactory var3) throws BeansException;

    Object instantiate(RootBeanDefinition var1, @Nullable String var2, BeanFactory var3, Constructor<?> var4, Object... var5) throws BeansException;

    Object instantiate(RootBeanDefinition var1, @Nullable String var2, BeanFactory var3, @Nullable Object var4, Method var5, Object... var6) throws BeansException;
}
