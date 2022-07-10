package com.ant.yun.beans.factory.parsing;

import com.ant.yun.beans.BeanMetadataElement;
import com.ant.yun.core.io.Resource;
import com.ant.yun.lang.Nullable;
import com.ant.yun.util.Assert;

/**
 * @author jack_yun
 * @version 1.0
 * @description: TODO
 * @date 2022/7/9 15:40
 */
public class ImportDefinition implements BeanMetadataElement {
    private final String importedResource;
    @Nullable
    private final Resource[] actualResources;
    @Nullable
    private final Object source;

    public ImportDefinition(String importedResource) {
        this(importedResource, (Resource[])null, (Object)null);
    }

    public ImportDefinition(String importedResource, @Nullable Object source) {
        this(importedResource, (Resource[])null, source);
    }

    public ImportDefinition(String importedResource, @Nullable Resource[] actualResources, @Nullable Object source) {
        Assert.notNull(importedResource, "Imported resource must not be null");
        this.importedResource = importedResource;
        this.actualResources = actualResources;
        this.source = source;
    }

    public final String getImportedResource() {
        return this.importedResource;
    }

    @Nullable
    public final Resource[] getActualResources() {
        return this.actualResources;
    }

    @Nullable
    public final Object getSource() {
        return this.source;
    }
}
