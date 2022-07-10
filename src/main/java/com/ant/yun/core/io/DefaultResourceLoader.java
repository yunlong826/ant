package com.ant.yun.core.io;

import com.ant.yun.lang.Nullable;
import com.ant.yun.util.Assert;
import com.ant.yun.util.ClassUtils;
import com.ant.yun.util.ResourceUtils;
import com.ant.yun.util.StringUtils;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author jack_yun
 * @version 1.0
 * @description: TODO
 * @date 2022/7/9 11:02
 */
public class DefaultResourceLoader implements ResourceLoader{

    @Nullable
    private ClassLoader classLoader;


    private final Set<ProtocolResolver> protocolResolvers = new LinkedHashSet(4);

    public DefaultResourceLoader() {
        this.classLoader = ClassUtils.getDefaultClassLoader();
    }

    public DefaultResourceLoader(@Nullable ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    public void setClassLoader(@Nullable ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    public Collection<ProtocolResolver> getProtocolResolvers() {
        return this.protocolResolvers;
    }

    public void addProtocolResolver(ProtocolResolver resolver) {
        Assert.notNull(resolver, "ProtocolResolver must not be null");
        this.protocolResolvers.add(resolver);
    }



    @Override
    public Resource getResource(String location) {
        Assert.notNull(location, "Location must not be null");
        // 首先，通过 ProtocolResolver 来加载资源
        Iterator var2 = this.getProtocolResolvers().iterator();

        Resource resource;
        do{
            if(!var2.hasNext()){
                if(location.startsWith("/")){
                    return this.getResourceByPath(location);
                }
                if(location.startsWith("classpath:")){
                    return new ClassPathResource(location.substring("classpath:".length()),this.getClassLoader());
                }

                try{
                    URL url = new URL(location);
                    return (Resource)(ResourceUtils.isFileURL(url) ? new FileUrlResource(url) : new UrlResource(url));
                }catch (MalformedURLException var5){
                    // 没有相应的资源类型，那么它就会在抛出 MalformedURLException 异常
                    return this.getResourceByPath(location);
                }
            }
            ProtocolResolver protocolResolver = (ProtocolResolver)var2.next();
            resource = protocolResolver.resolve(location, this);
        }while(resource == null);
        return resource;
    }

    protected Resource getResourceByPath(String path) {
        return new DefaultResourceLoader.ClassPathContextResource(path, this.getClassLoader());
    }
    protected static class ClassPathContextResource extends ClassPathResource implements ContextResource {
        public ClassPathContextResource(String path, @Nullable ClassLoader classLoader) {
            super(path, classLoader);
        }

        public String getPathWithinContext() {
            return this.getPath();
        }

        public Resource createRelative(String relativePath) {
            String pathToUse = StringUtils.applyRelativePath(this.getPath(), relativePath);
            return new DefaultResourceLoader.ClassPathContextResource(pathToUse, this.getClassLoader());
        }
    }

    @Override
    public ClassLoader getClassLoader() {
        return null;
    }
}
