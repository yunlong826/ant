package com.ant.yun.beans.factory.parsing;

import com.ant.yun.beans.BeanMetadataElement;
import com.ant.yun.beans.factory.config.BeanDefinition;
import com.ant.yun.beans.factory.config.BeanReference;

/**
 * @author jack_yun
 * @version 1.0
 * @description: TODO
 * @date 2022/7/9 15:38
 */
public interface ComponentDefinition extends BeanMetadataElement {
    String getName();

    String getDescription();

    BeanDefinition[] getBeanDefinitions();

    BeanDefinition[] getInnerBeanDefinitions();

    BeanReference[] getBeanReferences();
}
