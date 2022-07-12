package com.ant.yun.beans;

import java.beans.PropertyDescriptor;

/**
 * @author jack_yun
 * @version 1.0
 * @description: TODO
 * @date 2022/7/12 21:10
 */
public interface BeanWrapper extends ConfigurablePropertyAccessor {
    void setAutoGrowCollectionLimit(int var1);

    int getAutoGrowCollectionLimit();

    Object getWrappedInstance();

    Class<?> getWrappedClass();

    PropertyDescriptor[] getPropertyDescriptors();

    PropertyDescriptor getPropertyDescriptor(String var1) throws InvalidPropertyException;
}
