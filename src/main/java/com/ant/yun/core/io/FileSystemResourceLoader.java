package com.ant.yun.core.io;

/**
 * @author jack_yun
 * @version 1.0
 * @description: TODO
 * @date 2022/7/10 21:28
 */
public class FileSystemResourceLoader extends DefaultResourceLoader {
    public FileSystemResourceLoader() {
    }

    protected Resource getResourceByPath(String path) {
        if (path.startsWith("/")) {
            path = path.substring(1);
        }

        return new FileSystemResourceLoader.FileSystemContextResource(path);
    }

    private static class FileSystemContextResource extends FileSystemResource implements ContextResource {
        public FileSystemContextResource(String path) {
            super(path);
        }

        public String getPathWithinContext() {
            return this.getPath();
        }
    }
}
