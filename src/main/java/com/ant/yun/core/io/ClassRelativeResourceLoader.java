package com.ant.yun.core.io;

import com.ant.yun.util.Assert;
import com.ant.yun.util.StringUtils;

/**
 * @author jack_yun
 * @version 1.0
 * @description: TODO
 * @date 2022/7/10 21:31
 */
public class ClassRelativeResourceLoader extends DefaultResourceLoader{
    private final Class<?> clazz;

    public ClassRelativeResourceLoader(Class<?> clazz) {
        Assert.notNull(clazz, "Class must not be null");
        this.clazz = clazz;
        this.setClassLoader(clazz.getClassLoader());
    }

    protected Resource getResourceByPath(String path) {
        return new ClassRelativeResourceLoader.ClassRelativeContextResource(path, this.clazz);
    }

    private static class ClassRelativeContextResource extends ClassPathResource implements ContextResource {
        private final Class<?> clazz;

        public ClassRelativeContextResource(String path, Class<?> clazz) {
            super(path, clazz);
            this.clazz = clazz;
        }

        public String getPathWithinContext() {
            return this.getPath();
        }

        public Resource createRelative(String relativePath) {
            String pathToUse = StringUtils.applyRelativePath(this.getPath(), relativePath);
            return new ClassRelativeResourceLoader.ClassRelativeContextResource(pathToUse, this.clazz);
        }
    }
}
