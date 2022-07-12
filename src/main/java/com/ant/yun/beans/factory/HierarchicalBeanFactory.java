package com.ant.yun.beans.factory;

import com.ant.yun.lang.Nullable;

/**
 * @author jack_yun
 * @version 1.0
 * @description: TODO
 * @date 2022/7/12 20:23
 */
public interface HierarchicalBeanFactory extends BeanFactory{
    @Nullable
    BeanFactory getParentBeanFactory();

    boolean containsLocalBean(String var1);
}
