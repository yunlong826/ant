package com.ant.yun.beans.factory.config;

import com.ant.yun.lang.Nullable;
import com.ant.yun.util.Assert;

/**
 * @author jack_yun
 * @version 1.0
 * @description: TODO
 * @date 2022/7/9 16:44
 */
public class RuntimeBeanNameReference implements BeanReference{
    private final String beanName;
    @Nullable
    private Object source;

    public RuntimeBeanNameReference(String beanName) {
        Assert.hasText(beanName, "'beanName' must not be empty");
        this.beanName = beanName;
    }

    public String getBeanName() {
        return this.beanName;
    }

    public void setSource(@Nullable Object source) {
        this.source = source;
    }

    @Nullable
    public Object getSource() {
        return this.source;
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        } else if (!(other instanceof RuntimeBeanNameReference)) {
            return false;
        } else {
            RuntimeBeanNameReference that = (RuntimeBeanNameReference)other;
            return this.beanName.equals(that.beanName);
        }
    }

    public int hashCode() {
        return this.beanName.hashCode();
    }

    public String toString() {
        return '<' + this.getBeanName() + '>';
    }
}
