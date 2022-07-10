package com.ant.yun.beans.factory.support;

import com.ant.yun.beans.factory.config.BeanDefinition;
import com.ant.yun.core.io.AbstractResource;
import com.ant.yun.core.io.Resource;
import com.ant.yun.lang.Nullable;
import com.ant.yun.util.Assert;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author jack_yun
 * @version 1.0
 * @description: TODO
 * @date 2022/7/9 16:28
 */
public class BeanDefinitionResource extends AbstractResource {
    private final BeanDefinition beanDefinition;

    public BeanDefinitionResource(BeanDefinition beanDefinition) {
        Assert.notNull(beanDefinition, "BeanDefinition must not be null");
        this.beanDefinition = beanDefinition;
    }

    public final BeanDefinition getBeanDefinition() {
        return this.beanDefinition;
    }

    public boolean exists() {
        return false;
    }

    public boolean isReadable() {
        return false;
    }

    public InputStream getInputStream() throws IOException {
        throw new FileNotFoundException("Resource cannot be opened because it points to " + this.getDescription());
    }

    public String getDescription() {
        return "BeanDefinition defined in " + this.beanDefinition.getResourceDescription();
    }

    public boolean equals(@Nullable Object other) {
        return this == other || other instanceof BeanDefinitionResource && ((BeanDefinitionResource)other).beanDefinition.equals(this.beanDefinition);
    }

    public int hashCode() {
        return this.beanDefinition.hashCode();
    }
}
