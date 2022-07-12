package com.ant.yun.beans.factory.support;

import com.ant.yun.beans.BeansException;
import com.ant.yun.beans.factory.BeanCreationException;
import com.ant.yun.beans.factory.BeanCurrentlyInCreationException;
import com.ant.yun.beans.factory.FactoryBean;
import com.ant.yun.beans.factory.FactoryBeanNotInitializedException;
import com.ant.yun.lang.Nullable;

import java.security.AccessControlContext;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author jack_yun
 * @version 1.0
 * @description: TODO
 * @date 2022/7/12 20:14
 */
public abstract class FactoryBeanRegistrySupport extends DefaultSingletonBeanRegistry{
    private final Map<String, Object> factoryBeanObjectCache = new ConcurrentHashMap(16);

    public FactoryBeanRegistrySupport() {
    }

    @Nullable
    protected Class<?> getTypeForFactoryBean(FactoryBean<?> factoryBean) {
        try {
            return System.getSecurityManager() != null ? (Class) AccessController.doPrivileged(factoryBean::getObjectType, this.getAccessControlContext()) : factoryBean.getObjectType();
        } catch (Throwable var3) {
            this.logger.info("FactoryBean threw exception from getObjectType, despite the contract saying that it should return null if the type of its object cannot be determined yet", var3);
            return null;
        }
    }

    @Nullable
    protected Object getCachedObjectForFactoryBean(String beanName) {
        return this.factoryBeanObjectCache.get(beanName);
    }

    protected Object getObjectFromFactoryBean(FactoryBean<?> factory, String beanName, boolean shouldPostProcess) {
        if (factory.isSingleton() && this.containsSingleton(beanName)) {
            synchronized(this.getSingletonMutex()) {
                Object object = this.factoryBeanObjectCache.get(beanName);
                if (object == null) {
                    object = this.doGetObjectFromFactoryBean(factory, beanName);
                    Object alreadyThere = this.factoryBeanObjectCache.get(beanName);
                    if (alreadyThere != null) {
                        object = alreadyThere;
                    } else {
                        //  需要后续处理
                        if (shouldPostProcess) {
                            // 若该 Bean 处于创建中，则返回非处理对象，而不是存储它
                            if (this.isSingletonCurrentlyInCreation(beanName)) {
                                return object;
                            }
                            // 单例 Bean 的前置处理
                            this.beforeSingletonCreation(beanName);

                            try {
                                // 对从 FactoryBean 获取的对象进行后处理
                                // 生成的对象将暴露给 bean 引用
                                object = this.postProcessObjectFromFactoryBean(object, beanName);
                            } catch (Throwable var14) {
                                throw new BeanCreationException(beanName, "Post-processing of FactoryBean's singleton object failed", var14);
                            } finally {
                                // 单例 Bean 的后置处理
                                this.afterSingletonCreation(beanName);
                            }
                        }

                        if (this.containsSingleton(beanName)) {
                            //  添加到 factoryBeanObjectCache 中，进行缓存
                            this.factoryBeanObjectCache.put(beanName, object);
                        }
                    }
                }

                return object;
            }
        } else {
            Object object = this.doGetObjectFromFactoryBean(factory, beanName);
            if (shouldPostProcess) {
                try {
                    object = this.postProcessObjectFromFactoryBean(object, beanName);
                } catch (Throwable var17) {
                    throw new BeanCreationException(beanName, "Post-processing of FactoryBean's object failed", var17);
                }
            }

            return object;
        }
    }

    private Object doGetObjectFromFactoryBean(FactoryBean<?> factory, String beanName) throws BeanCreationException {
        Object object;
        try {
            // 需要权限验证
            if (System.getSecurityManager() != null) {
                AccessControlContext acc = this.getAccessControlContext();

                try {
                    object = AccessController.doPrivileged(factory::getObject, acc);
                } catch (PrivilegedActionException var6) {
                    throw var6.getException();
                }
            } else {
                object = factory.getObject();
            }
        } catch (FactoryBeanNotInitializedException var7) {
            throw new BeanCurrentlyInCreationException(beanName, var7.toString());
        } catch (Throwable var8) {
            throw new BeanCreationException(beanName, "FactoryBean threw exception on object creation", var8);
        }

        if (object == null) {
            if (this.isSingletonCurrentlyInCreation(beanName)) {
                throw new BeanCurrentlyInCreationException(beanName, "FactoryBean which is currently in creation returned null from getObject");
            }

            object = new NullBean();
        }

        return object;
    }

    protected Object postProcessObjectFromFactoryBean(Object object, String beanName) throws BeansException {
        return object;
    }

    protected FactoryBean<?> getFactoryBean(String beanName, Object beanInstance) throws BeansException {
        if (!(beanInstance instanceof FactoryBean)) {
            throw new BeanCreationException(beanName, "Bean instance of type [" + beanInstance.getClass() + "] is not a FactoryBean");
        } else {
            return (FactoryBean)beanInstance;
        }
    }

    protected void removeSingleton(String beanName) {
        synchronized(this.getSingletonMutex()) {
            super.removeSingleton(beanName);
            this.factoryBeanObjectCache.remove(beanName);
        }
    }

    protected void clearSingletonCache() {
        synchronized(this.getSingletonMutex()) {
            super.clearSingletonCache();
            this.factoryBeanObjectCache.clear();
        }
    }

    protected AccessControlContext getAccessControlContext() {
        return AccessController.getContext();
    }
}
