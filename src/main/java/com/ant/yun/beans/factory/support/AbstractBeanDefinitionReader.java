package com.ant.yun.beans.factory.support;

import com.ant.yun.beans.factory.BeanDefinitionStoreException;
import com.ant.yun.core.env.Environment;
import com.ant.yun.core.env.EnvironmentCapable;
import com.ant.yun.core.env.StandardEnvironment;
import com.ant.yun.core.io.Resource;
import com.ant.yun.core.io.ResourceLoader;
import com.ant.yun.core.io.support.PathMatchingResourcePatternResolver;
import com.ant.yun.core.io.support.ResourcePatternResolver;
import com.ant.yun.lang.Nullable;
import com.ant.yun.util.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Collections;
import java.util.Set;

/**
 * @author jack_yun
 * @version 1.0
 * @description: TODO
 * @date 2022/7/9 14:00
 */
public abstract class AbstractBeanDefinitionReader implements BeanDefinitionReader, EnvironmentCapable {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final BeanDefinitionRegistry registry;
    @Nullable
    private ResourceLoader resourceLoader;
    @Nullable
    private ClassLoader beanClassLoader;
    private Environment environment;
    private BeanNameGenerator beanNameGenerator;

    protected AbstractBeanDefinitionReader(BeanDefinitionRegistry registry) {
        this.beanNameGenerator = DefaultBeanNameGenerator.INSTANCE;
        Assert.notNull(registry, "BeanDefinitionRegistry must not be null");
        this.registry = registry;
        if (this.registry instanceof ResourceLoader) {
            this.resourceLoader = (ResourceLoader)this.registry;
        } else {
            this.resourceLoader = new PathMatchingResourcePatternResolver();
        }

        if (this.registry instanceof EnvironmentCapable) {
            this.environment = ((EnvironmentCapable)this.registry).getEnvironment();
        } else {
            this.environment = new StandardEnvironment();
        }

    }

    public final BeanDefinitionRegistry getBeanFactory() {
        return this.registry;
    }

    public final BeanDefinitionRegistry getRegistry() {
        return this.registry;
    }

    public void setResourceLoader(@Nullable ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Nullable
    public ResourceLoader getResourceLoader() {
        return this.resourceLoader;
    }

    public void setBeanClassLoader(@Nullable ClassLoader beanClassLoader) {
        this.beanClassLoader = beanClassLoader;
    }

    @Nullable
    public ClassLoader getBeanClassLoader() {
        return this.beanClassLoader;
    }

    public void setEnvironment(Environment environment) {
        Assert.notNull(environment, "Environment must not be null");
        this.environment = environment;
    }

    public Environment getEnvironment() {
        return this.environment;
    }

    public void setBeanNameGenerator(@Nullable BeanNameGenerator beanNameGenerator) {
        this.beanNameGenerator = (BeanNameGenerator)(beanNameGenerator != null ? beanNameGenerator : DefaultBeanNameGenerator.INSTANCE);
    }

    public BeanNameGenerator getBeanNameGenerator() {
        return this.beanNameGenerator;
    }

    public int loadBeanDefinitions(Resource... resources) throws BeanDefinitionStoreException {
        Assert.notNull(resources, "Resource array must not be null");
        int count = 0;
        Resource[] var3 = resources;
        int var4 = resources.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            Resource resource = var3[var5];
            count += this.loadBeanDefinitions((Resource)resource);
        }

        return count;
    }

    public int loadBeanDefinitions(String location) throws BeanDefinitionStoreException {
        return this.loadBeanDefinitions(location, (Set)null);
    }

    public int loadBeanDefinitions(String location, @Nullable Set<Resource> actualResources) throws BeanDefinitionStoreException {
        ResourceLoader resourceLoader = this.getResourceLoader();
        if (resourceLoader == null) {
            throw new BeanDefinitionStoreException("Cannot load bean definitions from location [" + location + "]: no ResourceLoader available");
        } else {
            int count;
            if (resourceLoader instanceof ResourcePatternResolver) {
                try {
                    Resource[] resources = ((ResourcePatternResolver)resourceLoader).getResources(location);
                    count = this.loadBeanDefinitions(resources);
                    if (actualResources != null) {
                        Collections.addAll(actualResources, resources);
                    }

                    if (this.logger.isTraceEnabled()) {
                        this.logger.trace("Loaded " + count + " bean definitions from location pattern [" + location + "]");
                    }

                    return count;
                } catch (IOException var6) {
                    throw new BeanDefinitionStoreException("Could not resolve bean definition resource pattern [" + location + "]", var6);
                }
            } else {
                Resource resource = resourceLoader.getResource(location);
                count = this.loadBeanDefinitions((Resource)resource);
                if (actualResources != null) {
                    actualResources.add(resource);
                }

                if (this.logger.isTraceEnabled()) {
                    this.logger.trace("Loaded " + count + " bean definitions from location [" + location + "]");
                }

                return count;
            }
        }
    }

    public int loadBeanDefinitions(String... locations) throws BeanDefinitionStoreException {
        Assert.notNull(locations, "Location array must not be null");
        int count = 0;
        String[] var3 = locations;
        int var4 = locations.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            String location = var3[var5];
            count += this.loadBeanDefinitions(location);
        }

        return count;
    }
}
