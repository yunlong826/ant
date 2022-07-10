package com.ant.yun.beans.factory.support;

import com.ant.yun.beans.factory.config.BeanDefinition;

/**
 * @author jack_yun
 * @version 1.0
 * @description: TODO
 * @date 2022/7/9 13:19
 */
public interface BeanNameGenerator {
    String generateBeanName(BeanDefinition var1, BeanDefinitionRegistry var2);
}
