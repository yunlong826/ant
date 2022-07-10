package com.ant.yun.core;

import com.ant.yun.lang.Nullable;
import com.ant.yun.util.ConcurrentReferenceHashMap;
import com.ant.yun.util.ObjectUtils;

import java.io.Serializable;
import java.lang.reflect.*;

/**
 * @author jack_yun
 * @version 1.0
 * @description: TODO
 * @date 2022/7/9 13:21
 */
public class ResolvableType implements Serializable {

    public static final ResolvableType NONE;
    private static final ResolvableType[] EMPTY_TYPES_ARRAY;
    private static final ConcurrentReferenceHashMap<ResolvableType, ResolvableType> cache;
    private final Type type;
    @Nullable
    private final SerializableTypeWrapper.TypeProvider typeProvider;
    @Nullable
    private final ResolvableType.VariableResolver variableResolver;
    @Nullable
    private final ResolvableType componentType;
    @Nullable
    private final Integer hash;
    @Nullable
    private Class<?> resolved;
    @Nullable
    private volatile ResolvableType superType;
    @Nullable
    private volatile ResolvableType[] interfaces;
    @Nullable
    private volatile ResolvableType[] generics;


    static {
        NONE = new ResolvableType(ResolvableType.EmptyType.INSTANCE, (SerializableTypeWrapper.TypeProvider)null, (ResolvableType.VariableResolver)null, 0);
        EMPTY_TYPES_ARRAY = new ResolvableType[0];
        cache = new ConcurrentReferenceHashMap(256);
    }

    private ResolvableType(Type type, @Nullable SerializableTypeWrapper.TypeProvider typeProvider, @Nullable ResolvableType.VariableResolver variableResolver) {
        this.type = type;
        this.typeProvider = typeProvider;
        this.variableResolver = variableResolver;
        this.componentType = null;
        this.hash = this.calculateHashCode();
        this.resolved = null;
    }

    private ResolvableType(Type type, @Nullable SerializableTypeWrapper.TypeProvider typeProvider, @Nullable ResolvableType.VariableResolver variableResolver, @Nullable Integer hash) {
        this.type = type;
        this.typeProvider = typeProvider;
        this.variableResolver = variableResolver;
        this.componentType = null;
        this.hash = hash;
        this.resolved = this.resolveClass();
    }

    private ResolvableType(Type type, @Nullable SerializableTypeWrapper.TypeProvider typeProvider, @Nullable ResolvableType.VariableResolver variableResolver, @Nullable ResolvableType componentType) {
        this.type = type;
        this.typeProvider = typeProvider;
        this.variableResolver = variableResolver;
        this.componentType = componentType;
        this.hash = null;
        this.resolved = this.resolveClass();
    }

    private ResolvableType(@Nullable Class<?> clazz) {
        this.resolved = clazz != null ? clazz : Object.class;
        this.type = this.resolved;
        this.typeProvider = null;
        this.variableResolver = null;
        this.componentType = null;
        this.hash = null;
    }

    private int calculateHashCode() {
        int hashCode = ObjectUtils.nullSafeHashCode(this.type);
        if (this.typeProvider != null) {
            hashCode = 31 * hashCode + ObjectUtils.nullSafeHashCode(this.typeProvider.getType());
        }

        if (this.variableResolver != null) {
            hashCode = 31 * hashCode + ObjectUtils.nullSafeHashCode(this.variableResolver.getSource());
        }

        if (this.componentType != null) {
            hashCode = 31 * hashCode + ObjectUtils.nullSafeHashCode(this.componentType);
        }

        return hashCode;
    }

    static class EmptyType implements Type, Serializable {
        static final Type INSTANCE = new ResolvableType.EmptyType();

        EmptyType() {
        }

        Object readResolve() {
            return INSTANCE;
        }
    }
    @Nullable
    private Class<?> resolveClass() {
        if (this.type == ResolvableType.EmptyType.INSTANCE) {
            return null;
        } else if (this.type instanceof Class) {
            return (Class)this.type;
        } else if (this.type instanceof GenericArrayType) {
            Class<?> resolvedComponent = this.getComponentType().resolve();
            return resolvedComponent != null ? Array.newInstance(resolvedComponent, 0).getClass() : null;
        } else {
            return this.resolveType().resolve();
        }
    }

    public ResolvableType getComponentType() {
        if (this == NONE) {
            return NONE;
        } else if (this.componentType != null) {
            return this.componentType;
        } else if (this.type instanceof Class) {
            Class<?> componentType = ((Class)this.type).getComponentType();
            return forType(componentType, (ResolvableType.VariableResolver)this.variableResolver);
        } else {
            return this.type instanceof GenericArrayType ? forType(((GenericArrayType)this.type).getGenericComponentType(), this.variableResolver) : this.resolveType().getComponentType();
        }
    }
    ResolvableType resolveType() {
        if (this.type instanceof ParameterizedType) {
            return forType(((ParameterizedType)this.type).getRawType(), this.variableResolver);
        } else if (this.type instanceof WildcardType) {
            Type resolved = this.resolveBounds(((WildcardType)this.type).getUpperBounds());
            if (resolved == null) {
                resolved = this.resolveBounds(((WildcardType)this.type).getLowerBounds());
            }

            return forType(resolved, this.variableResolver);
        } else if (this.type instanceof TypeVariable) {
            TypeVariable<?> variable = (TypeVariable)this.type;
            if (this.variableResolver != null) {
                ResolvableType resolved = this.variableResolver.resolveVariable(variable);
                if (resolved != null) {
                    return resolved;
                }
            }

            return forType(this.resolveBounds(variable.getBounds()), this.variableResolver);
        } else {
            return NONE;
        }
    }
    @Nullable
    private Type resolveBounds(Type[] bounds) {
        return bounds.length != 0 && bounds[0] != Object.class ? bounds[0] : null;
    }
    static ResolvableType forType(@Nullable Type type, @Nullable ResolvableType.VariableResolver variableResolver) {
        return forType(type, (SerializableTypeWrapper.TypeProvider)null, variableResolver);
    }

    static ResolvableType forType(@Nullable Type type, @Nullable SerializableTypeWrapper.TypeProvider typeProvider, @Nullable ResolvableType.VariableResolver variableResolver) {
        if (type == null && typeProvider != null) {
            type = SerializableTypeWrapper.forTypeProvider(typeProvider);
        }

        if (type == null) {
            return NONE;
        } else if (type instanceof Class) {
            return new ResolvableType(type, typeProvider, variableResolver, (ResolvableType)null);
        } else {
            cache.purgeUnreferencedEntries();
            ResolvableType resultType = new ResolvableType(type, typeProvider, variableResolver);
            ResolvableType cachedType = (ResolvableType)cache.get(resultType);
            if (cachedType == null) {
                cachedType = new ResolvableType(type, typeProvider, variableResolver, resultType.hash);
                cache.put(cachedType, cachedType);
            }

            resultType.resolved = cachedType.resolved;
            return resultType;
        }
    }

    public static ResolvableType forClass(@Nullable Class<?> clazz) {
        return new ResolvableType(clazz);
    }
    interface VariableResolver extends Serializable {
        Object getSource();

        @Nullable
        ResolvableType resolveVariable(TypeVariable<?> var1);
    }
    @Nullable
    public Class<?> resolve() {
        return this.resolved;
    }
}
