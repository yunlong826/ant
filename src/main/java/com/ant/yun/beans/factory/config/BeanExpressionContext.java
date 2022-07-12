package com.ant.yun.beans.factory.config;

import com.ant.yun.lang.Nullable;
import com.ant.yun.util.Assert;

/**
 * @author jack_yun
 * @version 1.0
 * @description: TODO
 * @date 2022/7/12 20:26
 */
public class BeanExpressionContext {
    private final ConfigurableBeanFactory beanFactory;
    @Nullable
    private final Scope scope;

    public BeanExpressionContext(ConfigurableBeanFactory beanFactory, @Nullable Scope scope) {
        Assert.notNull(beanFactory, "BeanFactory must not be null");
        this.beanFactory = beanFactory;
        this.scope = scope;
    }

    public final ConfigurableBeanFactory getBeanFactory() {
        return this.beanFactory;
    }

    @Nullable
    public final Scope getScope() {
        return this.scope;
    }

    public boolean containsObject(String key) {
        return this.beanFactory.containsBean(key) || this.scope != null && this.scope.resolveContextualObject(key) != null;
    }

    @Nullable
    public Object getObject(String key) {
        if (this.beanFactory.containsBean(key)) {
            return this.beanFactory.getBean(key);
        } else {
            return this.scope != null ? this.scope.resolveContextualObject(key) : null;
        }
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        } else if (!(other instanceof BeanExpressionContext)) {
            return false;
        } else {
            BeanExpressionContext otherContext = (BeanExpressionContext)other;
            return this.beanFactory == otherContext.beanFactory && this.scope == otherContext.scope;
        }
    }

    public int hashCode() {
        return this.beanFactory.hashCode();
    }
}
