package com.ant.yun.core.io;

import com.ant.yun.core.env.PropertyResolver;
import com.ant.yun.core.env.StandardEnvironment;
import com.ant.yun.lang.Nullable;
import com.ant.yun.util.Assert;
import com.ant.yun.util.StringUtils;

import java.beans.PropertyEditorSupport;
import java.io.IOException;

/**
 * @author jack_yun
 * @version 1.0
 * @description: TODO
 * @date 2022/7/12 20:51
 */
public class ResourceEditor extends PropertyEditorSupport {
    private final ResourceLoader resourceLoader;
    @Nullable
    private PropertyResolver propertyResolver;
    private final boolean ignoreUnresolvablePlaceholders;

    public ResourceEditor() {
        this(new DefaultResourceLoader(), (PropertyResolver)null);
    }

    public ResourceEditor(ResourceLoader resourceLoader, @Nullable PropertyResolver propertyResolver) {
        this(resourceLoader, propertyResolver, true);
    }

    public ResourceEditor(ResourceLoader resourceLoader, @Nullable PropertyResolver propertyResolver, boolean ignoreUnresolvablePlaceholders) {
        Assert.notNull(resourceLoader, "ResourceLoader must not be null");
        this.resourceLoader = resourceLoader;
        this.propertyResolver = propertyResolver;
        this.ignoreUnresolvablePlaceholders = ignoreUnresolvablePlaceholders;
    }

    public void setAsText(String text) {
        if (StringUtils.hasText(text)) {
            String locationToUse = this.resolvePath(text).trim();
            this.setValue(this.resourceLoader.getResource(locationToUse));
        } else {
            this.setValue((Object)null);
        }

    }

    protected String resolvePath(String path) {
        if (this.propertyResolver == null) {
            this.propertyResolver = new StandardEnvironment();
        }

        return this.ignoreUnresolvablePlaceholders ? this.propertyResolver.resolvePlaceholders(path) : this.propertyResolver.resolveRequiredPlaceholders(path);
    }

    @Nullable
    public String getAsText() {
        Resource value = (Resource)this.getValue();

        try {
            return value != null ? value.getURL().toExternalForm() : "";
        } catch (IOException var3) {
            return null;
        }
    }
}
