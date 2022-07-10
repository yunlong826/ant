package com.ant.yun.beans.factory.support;

import com.ant.yun.lang.Nullable;
import com.ant.yun.util.Assert;
import com.ant.yun.util.ObjectUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author jack_yun
 * @version 1.0
 * @description: TODO
 * @date 2022/7/9 16:35
 */
public class ReplaceOverride extends MethodOverride{
    private final String methodReplacerBeanName;
    private final List<String> typeIdentifiers = new ArrayList();

    public ReplaceOverride(String methodName, String methodReplacerBeanName) {
        super(methodName);
        Assert.notNull(methodReplacerBeanName, "Method replacer bean name must not be null");
        this.methodReplacerBeanName = methodReplacerBeanName;
    }

    public String getMethodReplacerBeanName() {
        return this.methodReplacerBeanName;
    }

    public void addTypeIdentifier(String identifier) {
        this.typeIdentifiers.add(identifier);
    }

    public boolean matches(Method method) {
        if (!method.getName().equals(this.getMethodName())) {
            return false;
        } else if (!this.isOverloaded()) {
            return true;
        } else if (this.typeIdentifiers.size() != method.getParameterCount()) {
            return false;
        } else {
            Class<?>[] parameterTypes = method.getParameterTypes();

            for(int i = 0; i < this.typeIdentifiers.size(); ++i) {
                String identifier = (String)this.typeIdentifiers.get(i);
                if (!parameterTypes[i].getName().contains(identifier)) {
                    return false;
                }
            }

            return true;
        }
    }

    public boolean equals(@Nullable Object other) {
        if (other instanceof ReplaceOverride && super.equals(other)) {
            ReplaceOverride that = (ReplaceOverride)other;
            return ObjectUtils.nullSafeEquals(this.methodReplacerBeanName, that.methodReplacerBeanName) && ObjectUtils.nullSafeEquals(this.typeIdentifiers, that.typeIdentifiers);
        } else {
            return false;
        }
    }

    public int hashCode() {
        int hashCode = super.hashCode();
        hashCode = 29 * hashCode + ObjectUtils.nullSafeHashCode(this.methodReplacerBeanName);
        hashCode = 29 * hashCode + ObjectUtils.nullSafeHashCode(this.typeIdentifiers);
        return hashCode;
    }

    public String toString() {
        return "Replace override for method '" + this.getMethodName() + "'";
    }
}
