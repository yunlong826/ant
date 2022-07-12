package com.ant.yun.beans.factory.config;

import com.ant.yun.beans.PropertyEditorRegistrar;
import com.ant.yun.beans.PropertyEditorRegistry;
import com.ant.yun.beans.factory.BeanDefinitionStoreException;
import com.ant.yun.beans.factory.BeanFactory;
import com.ant.yun.beans.factory.HierarchicalBeanFactory;
import com.ant.yun.beans.factory.NoSuchBeanDefinitionException;
import com.ant.yun.core.convert.ConversionService;
import com.ant.yun.lang.Nullable;
import com.ant.yun.util.StringValueResolver;
import sun.plugin.com.TypeConverter;

import java.beans.PropertyEditor;
import java.security.AccessControlContext;

/**
 * @author jack_yun
 * @version 1.0
 * @description: TODO
 * @date 2022/7/12 20:22
 */
public interface ConfigurableBeanFactory extends HierarchicalBeanFactory, SingletonBeanRegistry{
    String SCOPE_SINGLETON = "singleton";
    String SCOPE_PROTOTYPE = "prototype";

    void setParentBeanFactory(BeanFactory var1) throws IllegalStateException;

    void setBeanClassLoader(@Nullable ClassLoader var1);

    @Nullable
    ClassLoader getBeanClassLoader();

    void setTempClassLoader(@Nullable ClassLoader var1);

    @Nullable
    ClassLoader getTempClassLoader();

    void setCacheBeanMetadata(boolean var1);

    boolean isCacheBeanMetadata();

    void setBeanExpressionResolver(@Nullable BeanExpressionResolver var1);

    @Nullable
    BeanExpressionResolver getBeanExpressionResolver();

    void setConversionService(@Nullable ConversionService var1);

    @Nullable
    ConversionService getConversionService();

    void addPropertyEditorRegistrar(PropertyEditorRegistrar var1);

    void registerCustomEditor(Class<?> var1, Class<? extends PropertyEditor> var2);

    void copyRegisteredEditorsTo(PropertyEditorRegistry var1);

    void setTypeConverter(TypeConverter var1);

    TypeConverter getTypeConverter();

    void addEmbeddedValueResolver(StringValueResolver var1);

    boolean hasEmbeddedValueResolver();

    @Nullable
    String resolveEmbeddedValue(String var1);

    void addBeanPostProcessor(BeanPostProcessor var1);

    int getBeanPostProcessorCount();

    void registerScope(String var1, Scope var2);

    String[] getRegisteredScopeNames();

    @Nullable
    Scope getRegisteredScope(String var1);

    AccessControlContext getAccessControlContext();

    void copyConfigurationFrom(ConfigurableBeanFactory var1);

    void registerAlias(String var1, String var2) throws BeanDefinitionStoreException;

    void resolveAliases(StringValueResolver var1);

    BeanDefinition getMergedBeanDefinition(String var1) throws NoSuchBeanDefinitionException;

    boolean isFactoryBean(String var1) throws NoSuchBeanDefinitionException;

    void setCurrentlyInCreation(String var1, boolean var2);

    boolean isCurrentlyInCreation(String var1);

    void registerDependentBean(String var1, String var2);

    String[] getDependentBeans(String var1);

    String[] getDependenciesForBean(String var1);

    void destroyBean(String var1, Object var2);

    void destroyScopedBean(String var1);

    void destroySingletons();
}
