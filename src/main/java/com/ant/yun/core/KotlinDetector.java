package com.ant.yun.core;

import com.ant.yun.lang.Nullable;
import com.ant.yun.util.ClassUtils;

import java.lang.annotation.Annotation;

/**
 * @author jack_yun
 * @version 1.0
 * @description: TODO
 * @date 2022/7/9 16:14
 */
public abstract class KotlinDetector {
    @Nullable
    private static final Class<? extends Annotation> kotlinMetadata;
    private static final boolean kotlinReflectPresent;

    public KotlinDetector() {
    }

    public static boolean isKotlinPresent() {
        return kotlinMetadata != null;
    }

    public static boolean isKotlinReflectPresent() {
        return kotlinReflectPresent;
    }

    public static boolean isKotlinType(Class<?> clazz) {
        return kotlinMetadata != null && clazz.getDeclaredAnnotation(kotlinMetadata) != null;
    }

    static {
        ClassLoader classLoader = KotlinDetector.class.getClassLoader();

        Class metadata;
        try {
            metadata = ClassUtils.forName("kotlin.Metadata", classLoader);
        } catch (ClassNotFoundException var3) {
            metadata = null;
        }

        kotlinMetadata = metadata;
        kotlinReflectPresent = ClassUtils.isPresent("kotlin.reflect.full.KClasses", classLoader);
    }
}
