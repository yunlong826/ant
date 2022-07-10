package com.ant.yun.beans.factory.parsing;

import com.ant.yun.beans.factory.config.BeanDefinition;
import com.ant.yun.beans.factory.config.BeanReference;

/**
 * @author jack_yun
 * @version 1.0
 * @description: TODO
 * @date 2022/7/9 15:44
 */
public abstract class AbstractComponentDefinition implements ComponentDefinition{
    public AbstractComponentDefinition() {
    }

    public String getDescription() {
        return this.getName();
    }

    public BeanDefinition[] getBeanDefinitions() {
        return new BeanDefinition[0];
    }

    public BeanDefinition[] getInnerBeanDefinitions() {
        return new BeanDefinition[0];
    }

    public BeanReference[] getBeanReferences() {
        return new BeanReference[0];
    }

    public String toString() {
        return this.getDescription();
    }
}
