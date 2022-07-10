package com.ant.yun.beans.factory.parsing;

import com.ant.yun.core.io.Resource;
import com.ant.yun.lang.Nullable;
import com.ant.yun.util.Assert;

/**
 * @author jack_yun
 * @version 1.0
 * @description: TODO
 * @date 2022/7/9 15:35
 */
public class Location {
    private final Resource resource;
    @Nullable
    private final Object source;

    public Location(Resource resource) {
        this(resource, (Object)null);
    }

    public Location(Resource resource, @Nullable Object source) {
        Assert.notNull(resource, "Resource must not be null");
        this.resource = resource;
        this.source = source;
    }

    public Resource getResource() {
        return this.resource;
    }

    @Nullable
    public Object getSource() {
        return this.source;
    }
}
