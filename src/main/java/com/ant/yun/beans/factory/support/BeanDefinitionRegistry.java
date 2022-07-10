package com.ant.yun.beans.factory.support;

import com.ant.yun.beans.factory.BeanDefinitionStoreException;
import com.ant.yun.beans.factory.NoSuchBeanDefinitionException;
import com.ant.yun.beans.factory.config.BeanDefinition;
import com.ant.yun.core.AliasRegistry;

/**
 * @author jack_yun
 * @version 1.0
 * @description: TODO
 * @date 2022/7/9 13:20
 */
public interface BeanDefinitionRegistry extends AliasRegistry {
    void registerBeanDefinition(String var1, BeanDefinition var2) throws BeanDefinitionStoreException;

    void removeBeanDefinition(String var1) throws NoSuchBeanDefinitionException;

    BeanDefinition getBeanDefinition(String var1) throws NoSuchBeanDefinitionException;

    boolean containsBeanDefinition(String var1);

    String[] getBeanDefinitionNames();

    int getBeanDefinitionCount();

    boolean isBeanNameInUse(String var1);
}
