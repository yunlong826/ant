package com.ant.yun.core.io;

import com.ant.yun.lang.Nullable;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author jack_yun
 * @version 1.0
 * @description: TODO
 * @date 2022/7/9 16:08
 */
public class DescriptiveResource extends AbstractResource {
    private final String description;

    public DescriptiveResource(@Nullable String description) {
        this.description = description != null ? description : "";
    }

    public boolean exists() {
        return false;
    }

    public boolean isReadable() {
        return false;
    }

    public InputStream getInputStream() throws IOException {
        throw new FileNotFoundException(this.getDescription() + " cannot be opened because it does not point to a readable resource");
    }

    public String getDescription() {
        return this.description;
    }

    public boolean equals(@Nullable Object other) {
        return this == other || other instanceof DescriptiveResource && ((DescriptiveResource)other).description.equals(this.description);
    }

    public int hashCode() {
        return this.description.hashCode();
    }
}
