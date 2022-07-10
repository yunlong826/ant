package com.ant.yun.beans.factory.support;

import com.ant.yun.beans.factory.config.BeanDefinition;
import com.ant.yun.lang.Nullable;
import com.ant.yun.util.ObjectUtils;

/**
 * @author jack_yun
 * @version 1.0
 * @description: TODO
 * @date 2022/7/9 16:40
 */
public class GenericBeanDefinition extends AbstractBeanDefinition{
    @Nullable
    private String parentName;

    public GenericBeanDefinition() {
    }

    public GenericBeanDefinition(BeanDefinition original) {
        super(original);
    }

    public void setParentName(@Nullable String parentName) {
        this.parentName = parentName;
    }

    @Nullable
    public String getParentName() {
        return this.parentName;
    }

    public AbstractBeanDefinition cloneBeanDefinition() {
        return new GenericBeanDefinition(this);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        } else if (!(other instanceof GenericBeanDefinition)) {
            return false;
        } else {
            GenericBeanDefinition that = (GenericBeanDefinition)other;
            return ObjectUtils.nullSafeEquals(this.parentName, that.parentName) && super.equals(other);
        }
    }

    public String toString() {
        return this.parentName != null ? "Generic bean with parent '" + this.parentName + "': " + super.toString() : "Generic bean: " + super.toString();
    }
}
