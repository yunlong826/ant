package com.ant.yun.beans.factory.xml;

import com.ant.yun.beans.BeanUtils;
import com.ant.yun.beans.FatalBeanException;
import com.ant.yun.core.io.support.PropertiesLoaderUtils;
import com.ant.yun.lang.Nullable;
import com.ant.yun.util.Assert;
import com.ant.yun.util.ClassUtils;
import com.ant.yun.util.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author jack_yun
 * @version 1.0
 * @description: TODO
 * @date 2022/7/9 16:18
 */
public class DefaultNamespaceHandlerResolver implements NamespaceHandlerResolver {
    public static final String DEFAULT_HANDLER_MAPPINGS_LOCATION = "META-INF/spring.handlers";
    protected final Logger logger;
    @Nullable
    private final ClassLoader classLoader;
    private final String handlerMappingsLocation;
    @Nullable
    private volatile Map<String, Object> handlerMappings;

    public DefaultNamespaceHandlerResolver() {
        this((ClassLoader)null, "META-INF/spring.handlers");
    }

    public DefaultNamespaceHandlerResolver(@Nullable ClassLoader classLoader) {
        this(classLoader, "META-INF/spring.handlers");
    }

    public DefaultNamespaceHandlerResolver(@Nullable ClassLoader classLoader, String handlerMappingsLocation) {
        this.logger = LoggerFactory.getLogger(this.getClass());
        Assert.notNull(handlerMappingsLocation, "Handler mappings location must not be null");
        this.classLoader = classLoader != null ? classLoader : ClassUtils.getDefaultClassLoader();
        this.handlerMappingsLocation = handlerMappingsLocation;
    }

    @Nullable
    public NamespaceHandler resolve(String namespaceUri) {
        Map<String, Object> handlerMappings = this.getHandlerMappings();
        Object handlerOrClassName = handlerMappings.get(namespaceUri);
        if (handlerOrClassName == null) {
            return null;
        } else if (handlerOrClassName instanceof NamespaceHandler) {
            return (NamespaceHandler)handlerOrClassName;
        } else {
            String className = (String)handlerOrClassName;

            try {
                Class<?> handlerClass = ClassUtils.forName(className, this.classLoader);
                if (!NamespaceHandler.class.isAssignableFrom(handlerClass)) {
                    throw new FatalBeanException("Class [" + className + "] for namespace [" + namespaceUri + "] does not implement the [" + NamespaceHandler.class.getName() + "] interface");
                } else {
                    NamespaceHandler namespaceHandler = (NamespaceHandler) BeanUtils.instantiateClass(handlerClass);
                    namespaceHandler.init();
                    handlerMappings.put(namespaceUri, namespaceHandler);
                    return namespaceHandler;
                }
            } catch (ClassNotFoundException var7) {
                throw new FatalBeanException("Could not find NamespaceHandler class [" + className + "] for namespace [" + namespaceUri + "]", var7);
            } catch (LinkageError var8) {
                throw new FatalBeanException("Unresolvable class definition for NamespaceHandler class [" + className + "] for namespace [" + namespaceUri + "]", var8);
            }
        }
    }

    private Map<String, Object> getHandlerMappings() {
        Map<String, Object> handlerMappings = this.handlerMappings;
        if (handlerMappings == null) {
            synchronized(this) {
                handlerMappings = this.handlerMappings;
                if (handlerMappings == null) {
                    if (this.logger.isTraceEnabled()) {
                        this.logger.trace("Loading NamespaceHandler mappings from [" + this.handlerMappingsLocation + "]");
                    }

                    try {
                        Properties mappings = PropertiesLoaderUtils.loadAllProperties(this.handlerMappingsLocation, this.classLoader);
                        if (this.logger.isTraceEnabled()) {
                            this.logger.trace("Loaded NamespaceHandler mappings: " + mappings);
                        }

                        handlerMappings = new ConcurrentHashMap(mappings.size());
                        CollectionUtils.mergePropertiesIntoMap(mappings, (Map)handlerMappings);
                        this.handlerMappings = (Map)handlerMappings;
                    } catch (IOException var5) {
                        throw new IllegalStateException("Unable to load NamespaceHandler mappings from location [" + this.handlerMappingsLocation + "]", var5);
                    }
                }
            }
        }

        return (Map)handlerMappings;
    }

    public String toString() {
        return "NamespaceHandlerResolver using mappings " + this.getHandlerMappings();
    }
}
