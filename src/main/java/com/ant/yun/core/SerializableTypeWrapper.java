package com.ant.yun.core;

import com.ant.yun.lang.Nullable;
import com.ant.yun.util.ConcurrentReferenceHashMap;
import com.ant.yun.util.ObjectUtils;
import com.ant.yun.util.ReflectionUtils;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.lang.reflect.*;

/**
 * @author jack_yun
 * @version 1.0
 * @description: TODO
 * @date 2022/7/9 13:25
 */
final class SerializableTypeWrapper {
    private static final Class<?>[] SUPPORTED_SERIALIZABLE_TYPES = new Class[]{GenericArrayType.class, ParameterizedType.class, TypeVariable.class, WildcardType.class};
    static final ConcurrentReferenceHashMap<Type, Type> cache = new ConcurrentReferenceHashMap(256);

    private SerializableTypeWrapper() {
    }

    interface TypeProvider extends Serializable {
        @Nullable
        Type getType();

        @Nullable
        default Object getSource() {
            return null;
        }
    }

    @Nullable
    static Type forTypeProvider(SerializableTypeWrapper.TypeProvider provider) {
        Type providedType = provider.getType();
        if (providedType != null && !(providedType instanceof Serializable)) {
            if (!GraalDetector.inImageCode() && Serializable.class.isAssignableFrom(Class.class)) {
                Type cached = (Type)cache.get(providedType);
                if (cached != null) {
                    return cached;
                } else {
                    Class[] var3 = SUPPORTED_SERIALIZABLE_TYPES;
                    int var4 = var3.length;

                    for(int var5 = 0; var5 < var4; ++var5) {
                        Class<?> type = var3[var5];
                        if (type.isInstance(providedType)) {
                            ClassLoader classLoader = provider.getClass().getClassLoader();
                            Class<?>[] interfaces = new Class[]{type, SerializableTypeWrapper.SerializableTypeProxy.class, Serializable.class};
                            InvocationHandler handler = new SerializableTypeWrapper.TypeProxyInvocationHandler(provider);
                            cached = (Type) Proxy.newProxyInstance(classLoader, interfaces, handler);
                            cache.put(providedType, cached);
                            return cached;
                        }
                    }

                    throw new IllegalArgumentException("Unsupported Type class: " + providedType.getClass().getName());
                }
            } else {
                return providedType;
            }
        } else {
            return providedType;
        }
    }
    interface SerializableTypeProxy {
        SerializableTypeWrapper.TypeProvider getTypeProvider();
    }
    public static <T extends Type> T unwrap(T type) {
        Type unwrapped = null;
        if (type instanceof SerializableTypeWrapper.SerializableTypeProxy) {
            unwrapped = ((SerializableTypeWrapper.SerializableTypeProxy)type).getTypeProvider().getType();
        }

        return unwrapped != null ? (T) unwrapped : type;
    }
    private static class TypeProxyInvocationHandler implements InvocationHandler, Serializable {
        private final SerializableTypeWrapper.TypeProvider provider;

        public TypeProxyInvocationHandler(SerializableTypeWrapper.TypeProvider provider) {
            this.provider = provider;
        }

        @Nullable
        public Object invoke(Object proxy, Method method, @Nullable Object[] args) throws Throwable {
            if (method.getName().equals("equals") && args != null) {
                Object other = args[0];
                if (other instanceof Type) {
                    other = SerializableTypeWrapper.unwrap((Type)other);
                }

                return ObjectUtils.nullSafeEquals(this.provider.getType(), other);
            } else if (method.getName().equals("hashCode")) {
                return ObjectUtils.nullSafeHashCode(this.provider.getType());
            } else if (method.getName().equals("getTypeProvider")) {
                return this.provider;
            } else if (Type.class == method.getReturnType() && args == null) {
                return SerializableTypeWrapper.forTypeProvider(new SerializableTypeWrapper.MethodInvokeTypeProvider(this.provider, method, -1));
            } else if (Type[].class == method.getReturnType() && args == null) {
                Type[] result = new Type[((Type[])((Type[])method.invoke(this.provider.getType()))).length];

                for(int i = 0; i < result.length; ++i) {
                    result[i] = SerializableTypeWrapper.forTypeProvider(new SerializableTypeWrapper.MethodInvokeTypeProvider(this.provider, method, i));
                }

                return result;
            } else {
                try {
                    return method.invoke(this.provider.getType(), args);
                } catch (InvocationTargetException var6) {
                    throw var6.getTargetException();
                }
            }
        }
    }
    static class MethodInvokeTypeProvider implements SerializableTypeWrapper.TypeProvider {
        private final SerializableTypeWrapper.TypeProvider provider;
        private final String methodName;
        private final Class<?> declaringClass;
        private final int index;
        private transient Method method;
        @Nullable
        private transient volatile Object result;

        public MethodInvokeTypeProvider(SerializableTypeWrapper.TypeProvider provider, Method method, int index) {
            this.provider = provider;
            this.methodName = method.getName();
            this.declaringClass = method.getDeclaringClass();
            this.index = index;
            this.method = method;
        }

        @Nullable
        public Type getType() {
            Object result = this.result;
            if (result == null) {
                result = ReflectionUtils.invokeMethod(this.method, this.provider.getType());
                this.result = result;
            }

            return result instanceof Type[] ? ((Type[])((Type[])result))[this.index] : (Type)result;
        }

        @Nullable
        public Object getSource() {
            return null;
        }

        private void readObject(ObjectInputStream inputStream) throws IOException, ClassNotFoundException {
            inputStream.defaultReadObject();
            Method method = ReflectionUtils.findMethod(this.declaringClass, this.methodName);
            if (method == null) {
                throw new IllegalStateException("Cannot find method on deserialization: " + this.methodName);
            } else if (method.getReturnType() != Type.class && method.getReturnType() != Type[].class) {
                throw new IllegalStateException("Invalid return type on deserialized method - needs to be Type or Type[]: " + method);
            } else {
                this.method = method;
            }
        }
    }
}
