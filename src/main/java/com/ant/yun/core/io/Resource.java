package com.ant.yun.core.io;

import com.ant.yun.lang.Nullable;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

/**
 * @author jack_yun
 * @version 1.0
 * @description: TODO
 * @date 2022/7/9 10:23
 */
public interface Resource extends InputStreamSource{

    /**
     * 资源是否存在
     */
    boolean exists();

    /**
     * 资源是否可读
     */
    default boolean isReadable() {
        return this.exists();
    }

    /**
     * 资源所代表的句柄是否被一个 stream 打开了
     */
    default boolean isOpen() {
        return false;
    }

    /**
     * 是否为 File
     */
    default boolean isFile() {
        return false;
    }

    /**
     * 返回资源的 URL 的句柄
     */
    URL getURL() throws IOException;

    /**
     * 返回资源的 URI 的句柄
     */
    URI getURI() throws IOException;

    /**
     * 返回资源的 File 的句柄
     */
    File getFile() throws IOException;

    /**
     * 返回 ReadableByteChannel
     */
    default ReadableByteChannel readableChannel() throws IOException {
        return Channels.newChannel(this.getInputStream());
    }

    /**
     * 资源内容的长度
     */
    long contentLength() throws IOException;

    /**
     * 资源最后的修改时间
     */
    long lastModified() throws IOException;

    /**
     * 根据资源的相对路径创建新资源
     */
    Resource createRelative(String var1) throws IOException;

    /**
     * 资源的文件名
     */
    @Nullable
    String getFilename();

    /**
     * 资源的描述
     */
    String getDescription();
}
