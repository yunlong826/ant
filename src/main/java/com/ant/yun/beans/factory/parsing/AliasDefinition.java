package com.ant.yun.beans.factory.parsing;

import com.ant.yun.beans.BeanMetadataElement;
import com.ant.yun.lang.Nullable;
import com.ant.yun.util.Assert;

/**
 * @author jack_yun
 * @version 1.0
 * @description: TODO
 * @date 2022/7/9 15:39
 */
public class AliasDefinition implements BeanMetadataElement {
    private final String beanName;
    private final String alias;
    @Nullable
    private final Object source;

    public AliasDefinition(String beanName, String alias) {
        this(beanName, alias, (Object)null);
    }

    public AliasDefinition(String beanName, String alias, @Nullable Object source) {
        Assert.notNull(beanName, "Bean name must not be null");
        Assert.notNull(alias, "Alias must not be null");
        this.beanName = beanName;
        this.alias = alias;
        this.source = source;
    }

    public final String getBeanName() {
        return this.beanName;
    }

    public final String getAlias() {
        return this.alias;
    }

    @Nullable
    public final Object getSource() {
        return this.source;
    }
}
