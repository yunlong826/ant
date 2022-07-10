package com.ant.yun.beans.factory.config;

import com.ant.yun.lang.Nullable;
import com.ant.yun.util.Assert;

/**
 * @author jack_yun
 * @version 1.0
 * @description: TODO
 * @date 2022/7/9 16:42
 */
public class RuntimeBeanReference implements BeanReference {
    private final String beanName;
    @Nullable
    private final Class<?> beanType;
    private final boolean toParent;
    @Nullable
    private Object source;

    public RuntimeBeanReference(String beanName) {
        this(beanName, false);
    }

    public RuntimeBeanReference(String beanName, boolean toParent) {
        Assert.hasText(beanName, "'beanName' must not be empty");
        this.beanName = beanName;
        this.beanType = null;
        this.toParent = toParent;
    }

    public RuntimeBeanReference(Class<?> beanType) {
        this(beanType, false);
    }

    public RuntimeBeanReference(Class<?> beanType, boolean toParent) {
        Assert.notNull(beanType, "'beanType' must not be empty");
        this.beanName = beanType.getName();
        this.beanType = beanType;
        this.toParent = toParent;
    }

    public String getBeanName() {
        return this.beanName;
    }

    @Nullable
    public Class<?> getBeanType() {
        return this.beanType;
    }

    public boolean isToParent() {
        return this.toParent;
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
        } else if (!(other instanceof RuntimeBeanReference)) {
            return false;
        } else {
            RuntimeBeanReference that = (RuntimeBeanReference)other;
            return this.beanName.equals(that.beanName) && this.beanType == that.beanType && this.toParent == that.toParent;
        }
    }

    public int hashCode() {
        int result = this.beanName.hashCode();
        result = 29 * result + (this.toParent ? 1 : 0);
        return result;
    }

    public String toString() {
        return '<' + this.getBeanName() + '>';
    }
}
