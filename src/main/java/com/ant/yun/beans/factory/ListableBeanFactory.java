package com.ant.yun.beans.factory;

import com.ant.yun.beans.BeansException;
import com.ant.yun.core.ResolvableType;
import com.ant.yun.lang.Nullable;

import java.lang.annotation.Annotation;
import java.util.Map;

/**
 * @author jack_yun
 * @version 1.0
 * @description: TODO
 * @date 2022/7/12 20:38
 */
public interface ListableBeanFactory extends BeanFactory{
    boolean containsBeanDefinition(String var1);

    int getBeanDefinitionCount();

    String[] getBeanDefinitionNames();

    String[] getBeanNamesForType(ResolvableType var1);

    String[] getBeanNamesForType(@Nullable Class<?> var1);

    String[] getBeanNamesForType(@Nullable Class<?> var1, boolean var2, boolean var3);

    <T> Map<String, T> getBeansOfType(@Nullable Class<T> var1) throws BeansException;

    <T> Map<String, T> getBeansOfType(@Nullable Class<T> var1, boolean var2, boolean var3) throws BeansException;

    String[] getBeanNamesForAnnotation(Class<? extends Annotation> var1);

    Map<String, Object> getBeansWithAnnotation(Class<? extends Annotation> var1) throws BeansException;

    @Nullable
    <A extends Annotation> A findAnnotationOnBean(String var1, Class<A> var2) throws NoSuchBeanDefinitionException;
}
