package com.ant.yun.core.io.support;

import com.ant.yun.core.io.InputStreamSource;
import com.ant.yun.core.io.Resource;
import com.ant.yun.lang.Nullable;
import com.ant.yun.util.Assert;
import com.ant.yun.util.ObjectUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;

/**
 * @author jack_yun
 * @version 1.0
 * @description: TODO
 * @date 2022/7/9 15:58
 */
public class EncodedResource implements InputStreamSource {
    private final Resource resource;
    @Nullable
    private final String encoding;
    @Nullable
    private final Charset charset;

    public EncodedResource(Resource resource) {
        this(resource, (String)null, (Charset)null);
    }

    public EncodedResource(Resource resource, @Nullable String encoding) {
        this(resource, encoding, (Charset)null);
    }

    public EncodedResource(Resource resource, @Nullable Charset charset) {
        this(resource, (String)null, charset);
    }

    private EncodedResource(Resource resource, @Nullable String encoding, @Nullable Charset charset) {
        Assert.notNull(resource, "Resource must not be null");
        this.resource = resource;
        this.encoding = encoding;
        this.charset = charset;
    }
    public final Resource getResource() {
        return this.resource;
    }

    @Nullable
    public final String getEncoding() {
        return this.encoding;
    }

    @Nullable
    public final Charset getCharset() {
        return this.charset;
    }

    public boolean requiresReader() {
        return this.encoding != null || this.charset != null;
    }

    public Reader getReader() throws IOException {
        if (this.charset != null) {
            return new InputStreamReader(this.resource.getInputStream(), this.charset);
        } else {
            return this.encoding != null ? new InputStreamReader(this.resource.getInputStream(), this.encoding) : new InputStreamReader(this.resource.getInputStream());
        }
    }

    public InputStream getInputStream() throws IOException {
        return this.resource.getInputStream();
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        } else if (!(other instanceof EncodedResource)) {
            return false;
        } else {
            EncodedResource otherResource = (EncodedResource)other;
            return this.resource.equals(otherResource.resource) && ObjectUtils.nullSafeEquals(this.charset, otherResource.charset) && ObjectUtils.nullSafeEquals(this.encoding, otherResource.encoding);
        }
    }

    public int hashCode() {
        return this.resource.hashCode();
    }

    public String toString() {
        return this.resource.toString();
    }
}
