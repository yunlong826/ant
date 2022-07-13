package com.ant.yun.beans;

import com.ant.yun.core.KotlinDetector;
import com.ant.yun.lang.Nullable;
import com.ant.yun.util.Assert;
import com.ant.yun.util.ConcurrentReferenceHashMap;
import com.ant.yun.util.ReflectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.*;

/**
 * @author jack_yun
 * @version 1.0
 * @description: TODO
 * @date 2022/7/9 16:11
 */
public abstract class BeanUtils {
    private static final Logger logger = LoggerFactory.getLogger(BeanUtils.class);
    private static final Set<Class<?>> unknownEditorTypes = Collections.newSetFromMap(new ConcurrentReferenceHashMap(64));
    private static final Map<Class<?>, Object> DEFAULT_TYPE_VALUES;

    static {
        Map<Class<?>, Object> values = new HashMap();
        values.put(Boolean.TYPE, false);
        values.put(Byte.TYPE, (byte)0);
        values.put(Short.TYPE, Short.valueOf((short)0));
        values.put(Integer.TYPE, 0);
        values.put(Long.TYPE, 0L);
        DEFAULT_TYPE_VALUES = Collections.unmodifiableMap(values);
    }

    public BeanUtils() {
    }

    public static <T> T instantiateClass(Class<T> clazz) throws BeanInstantiationException {
        Assert.notNull(clazz, "Class must not be null");
        if (clazz.isInterface()) {
            throw new BeanInstantiationException(clazz, "Specified class is an interface");
        } else {
            try {
                return instantiateClass(clazz.getDeclaredConstructor());
            } catch (NoSuchMethodException var3) {
                Constructor<T> ctor = findPrimaryConstructor(clazz);
                if (ctor != null) {
                    return instantiateClass(ctor);
                } else {
                    throw new BeanInstantiationException(clazz, "No default constructor found", var3);
                }
            } catch (LinkageError var4) {
                throw new BeanInstantiationException(clazz, "Unresolvable class definition", var4);
            }
        }
    }
    @Nullable
    public static <T> Constructor<T> findPrimaryConstructor(Class<T> clazz) {
        Assert.notNull(clazz, "Class must not be null");
        if (KotlinDetector.isKotlinReflectPresent() && KotlinDetector.isKotlinType(clazz)) {
            Constructor<T> kotlinPrimaryConstructor = BeanUtils.KotlinDelegate.findPrimaryConstructor(clazz);
            if (kotlinPrimaryConstructor != null) {
                return kotlinPrimaryConstructor;
            }
        }

        return null;
    }
    public static <T> T instantiateClass(Constructor<T> ctor, Object... args) throws BeanInstantiationException {
        Assert.notNull(ctor, "Constructor must not be null");

        try {
            ReflectionUtils.makeAccessible(ctor);
            if (KotlinDetector.isKotlinReflectPresent() && KotlinDetector.isKotlinType(ctor.getDeclaringClass())) {
                return BeanUtils.KotlinDelegate.instantiateClass(ctor, args);
            } else {
                Class<?>[] parameterTypes = ctor.getParameterTypes();
                Assert.isTrue(args.length <= parameterTypes.length, "Can't specify more arguments than constructor parameters");
                Object[] argsWithDefaultValues = new Object[args.length];

                for(int i = 0; i < args.length; ++i) {
                    if (args[i] == null) {
                        Class<?> parameterType = parameterTypes[i];
                        argsWithDefaultValues[i] = parameterType.isPrimitive() ? DEFAULT_TYPE_VALUES.get(parameterType) : null;
                    } else {
                        argsWithDefaultValues[i] = args[i];
                    }
                }

                return ctor.newInstance(argsWithDefaultValues);
            }
        } catch (InstantiationException var6) {
            throw new BeanInstantiationException(ctor, "Is it an abstract class?", var6);
        } catch (IllegalAccessException var7) {
            throw new BeanInstantiationException(ctor, "Is the constructor accessible?", var7);
        } catch (IllegalArgumentException var8) {
            throw new BeanInstantiationException(ctor, "Illegal arguments for constructor", var8);
        } catch (InvocationTargetException var9) {
            throw new BeanInstantiationException(ctor, "Constructor threw exception", var9.getTargetException());
        }
    }
    private static class KotlinDelegate {
        private KotlinDelegate() {
        }

        @Nullable
        public static <T> Constructor<T> findPrimaryConstructor(Class<T> clazz) {
//            try {
//                KFunction<T> primaryCtor = KClasses.getPrimaryConstructor(JvmClassMappingKt.getKotlinClass(clazz));
//                if (primaryCtor == null) {
//                    return null;
//                } else {
//                    Constructor<T> constructor = ReflectJvmMapping.getJavaConstructor(primaryCtor);
//                    if (constructor == null) {
//                        throw new IllegalStateException("Failed to find Java constructor for Kotlin primary constructor: " + clazz.getName());
//                    } else {
//                        return constructor;
//                    }
//                }
//            } catch (UnsupportedOperationException var3) {
//                return null;
//            }
            return null;
        }

        public static <T> T instantiateClass(Constructor<T> ctor, Object... args) throws IllegalAccessException, InvocationTargetException, InstantiationException {
            KFunction<T> kotlinConstructor = ReflectJvmMapping.getKotlinFunction(ctor);
            if (kotlinConstructor == null) {
                return ctor.newInstance(args);
            } else {
                if (!Modifier.isPublic(ctor.getModifiers()) || !Modifier.isPublic(ctor.getDeclaringClass().getModifiers())) {
                    KCallablesJvm.setAccessible(kotlinConstructor, true);
                }

                List<KParameter> parameters = kotlinConstructor.getParameters();
                Map<KParameter, Object> argParameters = new HashMap(parameters.size());
                Assert.isTrue(args.length <= parameters.size(), "Number of provided arguments should be less of equals than number of constructor parameters");

                for(int i = 0; i < args.length; ++i) {
                    if (!((KParameter)parameters.get(i)).isOptional() || args[i] != null) {
                        argParameters.put(parameters.get(i), args[i]);
                    }
                }

                return kotlinConstructor.callBy(argParameters);
            }
            return null;
        }
    }
}
