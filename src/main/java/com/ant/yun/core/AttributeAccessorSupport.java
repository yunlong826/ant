package com.ant.yun.core;

import com.ant.yun.lang.Nullable;
import com.ant.yun.util.Assert;
import com.ant.yun.util.StringUtils;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author jack_yun
 * @version 1.0
 * @description: TODO
 * @date 2022/7/9 13:49
 */
public abstract class AttributeAccessorSupport implements AttributeAccessor, Serializable {
    private final Map<String, Object> attributes = new LinkedHashMap();

    public AttributeAccessorSupport() {
    }

    public void setAttribute(String name, @Nullable Object value) {
        Assert.notNull(name, "Name must not be null");
        if (value != null) {
            this.attributes.put(name, value);
        } else {
            this.removeAttribute(name);
        }

    }

    @Nullable
    public Object getAttribute(String name) {
        Assert.notNull(name, "Name must not be null");
        return this.attributes.get(name);
    }

    @Nullable
    public Object removeAttribute(String name) {
        Assert.notNull(name, "Name must not be null");
        return this.attributes.remove(name);
    }

    public boolean hasAttribute(String name) {
        Assert.notNull(name, "Name must not be null");
        return this.attributes.containsKey(name);
    }

    public String[] attributeNames() {
        return StringUtils.toStringArray(this.attributes.keySet());
    }

    protected void copyAttributesFrom(AttributeAccessor source) {
        Assert.notNull(source, "Source must not be null");
        String[] attributeNames = source.attributeNames();
        String[] var3 = attributeNames;
        int var4 = attributeNames.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            String attributeName = var3[var5];
            this.setAttribute(attributeName, source.getAttribute(attributeName));
        }

    }

    public boolean equals(@Nullable Object other) {
        return this == other || other instanceof AttributeAccessorSupport && this.attributes.equals(((AttributeAccessorSupport)other).attributes);
    }

    public int hashCode() {
        return this.attributes.hashCode();
    }
}
