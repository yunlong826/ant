package com.ant.yun.beans.factory.support;

import com.ant.yun.beans.BeanInstantiationException;
import com.ant.yun.beans.BeanUtils;
import com.ant.yun.beans.factory.BeanFactory;
import com.ant.yun.beans.factory.config.ConfigurableBeanFactory;
import com.ant.yun.lang.Nullable;
import com.ant.yun.util.ReflectionUtils;
import com.ant.yun.util.StringUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.AccessController;

/**
 * @author jack_yun
 * @version 1.0
 * @description: TODO
 * @date 2022/7/13 21:50
 */
public class SimpleInstantiationStrategy implements InstantiationStrategy {
    private static final ThreadLocal<Method> currentlyInvokedFactoryMethod = new ThreadLocal();

    public SimpleInstantiationStrategy() {
    }

    @Nullable
    public static Method getCurrentlyInvokedFactoryMethod() {
        return (Method)currentlyInvokedFactoryMethod.get();
    }

    public Object instantiate(RootBeanDefinition bd, @Nullable String beanName, BeanFactory owner) {
        if (!bd.hasMethodOverrides()) {
            Constructor constructorToUse;
            synchronized(bd.constructorArgumentLock) {
                constructorToUse = (Constructor)bd.resolvedConstructorOrFactoryMethod;
                if (constructorToUse == null) {
                    Class<?> clazz = bd.getBeanClass();
                    if (clazz.isInterface()) {
                        throw new BeanInstantiationException(clazz, "Specified class is an interface");
                    }

                    try {
                        if (System.getSecurityManager() != null) {
                            clazz.getClass();
                            constructorToUse = (Constructor) AccessController.doPrivileged(() -> {
                                return clazz.getDeclaredConstructor();
                            });
                        } else {
                            constructorToUse = clazz.getDeclaredConstructor();
                        }

                        bd.resolvedConstructorOrFactoryMethod = constructorToUse;
                    } catch (Throwable var9) {
                        throw new BeanInstantiationException(clazz, "No default constructor found", var9);
                    }
                }
            }

            // 通过 BeanUtils 直接使用构造器对象实例化 Bean 对象
            return BeanUtils.instantiateClass(constructorToUse, new Object[0]);
        } else {
            // 生成 CGLIB 创建的子类对象
            return this.instantiateWithMethodInjection(bd, beanName, owner);
        }
    }

    protected Object instantiateWithMethodInjection(RootBeanDefinition bd, @Nullable String beanName, BeanFactory owner) {
        throw new UnsupportedOperationException("Method Injection not supported in SimpleInstantiationStrategy");
    }

    public Object instantiate(RootBeanDefinition bd, @Nullable String beanName, BeanFactory owner, Constructor<?> ctor, Object... args) {
        if (!bd.hasMethodOverrides()) {
            if (System.getSecurityManager() != null) {
                AccessController.doPrivileged(() -> {
                    ReflectionUtils.makeAccessible(ctor);
                    return null;
                });
            }

            return BeanUtils.instantiateClass(ctor, args);
        } else {
            return this.instantiateWithMethodInjection(bd, beanName, owner, ctor, args);
        }
    }

    protected Object instantiateWithMethodInjection(RootBeanDefinition bd, @Nullable String beanName, BeanFactory owner, @Nullable Constructor<?> ctor, Object... args) {
        throw new UnsupportedOperationException("Method Injection not supported in SimpleInstantiationStrategy");
    }

    public Object instantiate(RootBeanDefinition bd, @Nullable String beanName, BeanFactory owner, @Nullable Object factoryBean, Method factoryMethod, Object... args) {
        try {
            if (System.getSecurityManager() != null) {
                AccessController.doPrivileged(() -> {
                    ReflectionUtils.makeAccessible(factoryMethod);
                    return null;
                });
            } else {
                ReflectionUtils.makeAccessible(factoryMethod);
            }

            Method priorInvokedFactoryMethod = (Method)currentlyInvokedFactoryMethod.get();

            Object var9;
            try {
                currentlyInvokedFactoryMethod.set(factoryMethod);
                Object result = factoryMethod.invoke(factoryBean, args);
                if (result == null) {
                    result = new NullBean();
                }

                var9 = result;
            } finally {
                if (priorInvokedFactoryMethod != null) {
                    currentlyInvokedFactoryMethod.set(priorInvokedFactoryMethod);
                } else {
                    currentlyInvokedFactoryMethod.remove();
                }

            }

            return var9;
        } catch (IllegalArgumentException var16) {
            throw new BeanInstantiationException(factoryMethod, "Illegal arguments to factory method '" + factoryMethod.getName() + "'; args: " + StringUtils.arrayToCommaDelimitedString(args), var16);
        } catch (IllegalAccessException var17) {
            throw new BeanInstantiationException(factoryMethod, "Cannot access factory method '" + factoryMethod.getName() + "'; is it public?", var17);
        } catch (InvocationTargetException var18) {
            String msg = "Factory method '" + factoryMethod.getName() + "' threw exception";
            if (bd.getFactoryBeanName() != null && owner instanceof ConfigurableBeanFactory && ((ConfigurableBeanFactory)owner).isCurrentlyInCreation(bd.getFactoryBeanName())) {
                msg = "Circular reference involving containing bean '" + bd.getFactoryBeanName() + "' - consider declaring the factory method as static for independence from its containing instance. " + msg;
            }

            throw new BeanInstantiationException(factoryMethod, msg, var18.getTargetException());
        }
    }
}
