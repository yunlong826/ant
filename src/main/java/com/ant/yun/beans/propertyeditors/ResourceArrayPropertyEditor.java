package com.ant.yun.beans.propertyeditors;

import com.ant.yun.core.env.PropertyResolver;
import com.ant.yun.core.env.StandardEnvironment;
import com.ant.yun.core.io.Resource;
import com.ant.yun.core.io.support.PathMatchingResourcePatternResolver;
import com.ant.yun.core.io.support.ResourcePatternResolver;
import com.ant.yun.lang.Nullable;
import com.ant.yun.util.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.PropertyEditorSupport;
import java.io.IOException;
import java.util.*;

/**
 * @author jack_yun
 * @version 1.0
 * @description: TODO
 * @date 2022/7/12 20:57
 */
public class ResourceArrayPropertyEditor extends PropertyEditorSupport {
    private static final Logger logger = LoggerFactory.getLogger(ResourceArrayPropertyEditor.class);
    private final ResourcePatternResolver resourcePatternResolver;
    @Nullable
    private PropertyResolver propertyResolver;
    private final boolean ignoreUnresolvablePlaceholders;

    public ResourceArrayPropertyEditor() {
        this(new PathMatchingResourcePatternResolver(), (PropertyResolver)null, true);
    }

    public ResourceArrayPropertyEditor(ResourcePatternResolver resourcePatternResolver, @Nullable PropertyResolver propertyResolver) {
        this(resourcePatternResolver, propertyResolver, true);
    }

    public ResourceArrayPropertyEditor(ResourcePatternResolver resourcePatternResolver, @Nullable PropertyResolver propertyResolver, boolean ignoreUnresolvablePlaceholders) {
        Assert.notNull(resourcePatternResolver, "ResourcePatternResolver must not be null");
        this.resourcePatternResolver = resourcePatternResolver;
        this.propertyResolver = propertyResolver;
        this.ignoreUnresolvablePlaceholders = ignoreUnresolvablePlaceholders;
    }

    public void setAsText(String text) {
        String pattern = this.resolvePath(text).trim();

        try {
            this.setValue(this.resourcePatternResolver.getResources(pattern));
        } catch (IOException var4) {
            throw new IllegalArgumentException("Could not resolve resource location pattern [" + pattern + "]: " + var4.getMessage());
        }
    }

    public void setValue(Object value) throws IllegalArgumentException {
        if (value instanceof Collection || value instanceof Object[] && !(value instanceof Resource[])) {
            Collection<?> input = value instanceof Collection ? (Collection)value : Arrays.asList((Object[])((Object[])value));
            List<Resource> merged = new ArrayList();
            Iterator var4 = ((Collection)input).iterator();

            while(true) {
                while(var4.hasNext()) {
                    Object element = var4.next();
                    if (element instanceof String) {
                        String pattern = this.resolvePath((String)element).trim();

                        try {
                            Resource[] resources = this.resourcePatternResolver.getResources(pattern);
                            Resource[] var8 = resources;
                            int var9 = resources.length;

                            for(int var10 = 0; var10 < var9; ++var10) {
                                Resource resource = var8[var10];
                                if (!merged.contains(resource)) {
                                    merged.add(resource);
                                }
                            }
                        } catch (IOException var12) {
                            if (logger.isDebugEnabled()) {
                                logger.debug("Could not retrieve resources for pattern '" + pattern + "'", var12);
                            }
                        }
                    } else {
                        if (!(element instanceof Resource)) {
                            throw new IllegalArgumentException("Cannot convert element [" + element + "] to [" + Resource.class.getName() + "]: only location String and Resource object supported");
                        }

                        Resource resource = (Resource)element;
                        if (!merged.contains(resource)) {
                            merged.add(resource);
                        }
                    }
                }

                super.setValue(merged.toArray(new Resource[0]));
                break;
            }
        } else {
            super.setValue(value);
        }

    }

    protected String resolvePath(String path) {
        if (this.propertyResolver == null) {
            this.propertyResolver = new StandardEnvironment();
        }

        return this.ignoreUnresolvablePlaceholders ? this.propertyResolver.resolvePlaceholders(path) : this.propertyResolver.resolveRequiredPlaceholders(path);
    }
}

