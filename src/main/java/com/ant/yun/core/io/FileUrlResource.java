package com.ant.yun.core.io;

import com.ant.yun.lang.Nullable;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

/**
 * @author jack_yun
 * @version 1.0
 * @description: TODO
 * @date 2022/7/9 12:58
 */
public class FileUrlResource extends UrlResource implements WritableResource{
    @Nullable
    private volatile File file;

    public FileUrlResource(URL url) {
        super(url);
    }

    public FileUrlResource(String location) throws MalformedURLException {
        super("file", location);
    }

    public File getFile() throws IOException {
        File file = this.file;
        if (file != null) {
            return file;
        } else {
            file = super.getFile();
            this.file = file;
            return file;
        }
    }

    public boolean isWritable() {
        try {
            File file = this.getFile();
            return file.canWrite() && !file.isDirectory();
        } catch (IOException var2) {
            return false;
        }
    }

    public OutputStream getOutputStream() throws IOException {
        return Files.newOutputStream(this.getFile().toPath());
    }

    public WritableByteChannel writableChannel() throws IOException {
        return FileChannel.open(this.getFile().toPath(), StandardOpenOption.WRITE);
    }

    public Resource createRelative(String relativePath) throws MalformedURLException {
        return new FileUrlResource(this.createRelativeURL(relativePath));
    }
}
