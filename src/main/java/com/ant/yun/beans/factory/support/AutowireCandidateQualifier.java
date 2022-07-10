package com.ant.yun.beans.factory.support;

import com.ant.yun.beans.BeanMetadataAttributeAccessor;
import com.ant.yun.util.Assert;

/**
 * @author jack_yun
 * @version 1.0
 * @description: TODO
 * @date 2022/7/9 16:26
 */
public class AutowireCandidateQualifier extends BeanMetadataAttributeAccessor {
    public static final String VALUE_KEY = "value";
    private final String typeName;

    public AutowireCandidateQualifier(Class<?> type) {
        this(type.getName());
    }

    public AutowireCandidateQualifier(String typeName) {
        Assert.notNull(typeName, "Type name must not be null");
        this.typeName = typeName;
    }

    public AutowireCandidateQualifier(Class<?> type, Object value) {
        this(type.getName(), value);
    }

    public AutowireCandidateQualifier(String typeName, Object value) {
        Assert.notNull(typeName, "Type name must not be null");
        this.typeName = typeName;
        this.setAttribute("value", value);
    }

    public String getTypeName() {
        return this.typeName;
    }
}
