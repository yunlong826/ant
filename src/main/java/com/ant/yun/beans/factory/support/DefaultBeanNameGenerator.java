package com.ant.yun.beans.factory.support;

import com.ant.yun.beans.factory.config.BeanDefinition;

/**
 * @author jack_yun
 * @version 1.0
 * @description: TODO
 * @date 2022/7/9 14:07
 */
public class DefaultBeanNameGenerator implements BeanNameGenerator{
    public static final DefaultBeanNameGenerator INSTANCE = new DefaultBeanNameGenerator();

    public DefaultBeanNameGenerator() {
    }

    public String generateBeanName(BeanDefinition definition, BeanDefinitionRegistry registry) {
        return BeanDefinitionReaderUtils.generateBeanName(definition, registry);
    }
}
