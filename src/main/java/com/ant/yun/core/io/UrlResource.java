package com.ant.yun.core.io;

import com.ant.yun.lang.Nullable;
import com.ant.yun.util.Assert;
import com.ant.yun.util.ResourceUtils;
import com.ant.yun.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.*;

/**
 * @author jack_yun
 * @version 1.0
 * @description: TODO
 * @date 2022/7/9 12:51
 */
public class UrlResource extends AbstractFileResolvingResource{
    @Nullable
    private final URI uri;
    private final URL url;
    @Nullable
    private volatile URL cleanedUrl;

    public UrlResource(URI uri) throws MalformedURLException {
        Assert.notNull(uri, "URI must not be null");
        this.uri = uri;
        this.url = uri.toURL();
    }

    public UrlResource(URL url) {
        Assert.notNull(url, "URL must not be null");
        this.uri = null;
        this.url = url;
    }

    public UrlResource(String path) throws MalformedURLException {
        Assert.notNull(path, "Path must not be null");
        this.uri = null;
        this.url = new URL(path);
        this.cleanedUrl = getCleanedUrl(this.url, path);
    }

    public UrlResource(String protocol, String location) throws MalformedURLException {
        this(protocol, location, (String)null);
    }

    public UrlResource(String protocol, String location, @Nullable String fragment) throws MalformedURLException {
        try {
            this.uri = new URI(protocol, location, fragment);
            this.url = this.uri.toURL();
        } catch (URISyntaxException var6) {
            MalformedURLException exToThrow = new MalformedURLException(var6.getMessage());
            exToThrow.initCause(var6);
            throw exToThrow;
        }
    }

    private static URL getCleanedUrl(URL originalUrl, String originalPath) {
        String cleanedPath = StringUtils.cleanPath(originalPath);
        if (!cleanedPath.equals(originalPath)) {
            try {
                return new URL(cleanedPath);
            } catch (MalformedURLException var4) {
            }
        }

        return originalUrl;
    }

    private URL getCleanedUrl() {
        URL cleanedUrl = this.cleanedUrl;
        if (cleanedUrl != null) {
            return cleanedUrl;
        } else {
            cleanedUrl = getCleanedUrl(this.url, (this.uri != null ? this.uri : this.url).toString());
            this.cleanedUrl = cleanedUrl;
            return cleanedUrl;
        }
    }

    public InputStream getInputStream() throws IOException {
        URLConnection con = this.url.openConnection();
        ResourceUtils.useCachesIfNecessary(con);

        try {
            return con.getInputStream();
        } catch (IOException var3) {
            if (con instanceof HttpURLConnection) {
                ((HttpURLConnection)con).disconnect();
            }

            throw var3;
        }
    }

    public URL getURL() {
        return this.url;
    }

    public URI getURI() throws IOException {
        return this.uri != null ? this.uri : super.getURI();
    }

    public boolean isFile() {
        return this.uri != null ? super.isFile(this.uri) : super.isFile();
    }

    public File getFile() throws IOException {
        return this.uri != null ? super.getFile(this.uri) : super.getFile();
    }

    public Resource createRelative(String relativePath) throws MalformedURLException {
        return new UrlResource(this.createRelativeURL(relativePath));
    }

    protected URL createRelativeURL(String relativePath) throws MalformedURLException {
        if (relativePath.startsWith("/")) {
            relativePath = relativePath.substring(1);
        }

        relativePath = StringUtils.replace(relativePath, "#", "%23");
        return new URL(this.url, relativePath);
    }

    public String getFilename() {
        return StringUtils.getFilename(this.getCleanedUrl().getPath());
    }

    public String getDescription() {
        return "URL [" + this.url + "]";
    }

    public boolean equals(@Nullable Object other) {
        return this == other || other instanceof UrlResource && this.getCleanedUrl().equals(((UrlResource)other).getCleanedUrl());
    }

    public int hashCode() {
        return this.getCleanedUrl().hashCode();
    }
}
