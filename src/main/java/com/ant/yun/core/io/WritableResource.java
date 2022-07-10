package com.ant.yun.core.io;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.channels.Channels;
import java.nio.channels.WritableByteChannel;

/**
 * @author jack_yun
 * @version 1.0
 * @description: TODO
 * @date 2022/7/9 12:57
 */
public interface WritableResource extends Resource{
    default boolean isWritable() {
        return true;
    }

    OutputStream getOutputStream() throws IOException;

    default WritableByteChannel writableChannel() throws IOException {
        return Channels.newChannel(this.getOutputStream());
    }
}
