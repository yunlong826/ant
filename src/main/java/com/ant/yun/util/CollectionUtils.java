package com.ant.yun.util;

import com.ant.yun.lang.Nullable;

import java.util.*;

/**
 * @author jack_yun
 * @version 1.0
 * @description: TODO
 * @date 2022/7/9 12:35
 */
public abstract class CollectionUtils {
    public CollectionUtils() {
    }

    public static boolean isEmpty(@Nullable Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    public static boolean isEmpty(@Nullable Map<?, ?> map) {
        return map == null || map.isEmpty();
    }

    public static <K, V> void mergePropertiesIntoMap(@Nullable Properties props, Map<String, Object> map) {
        String key;
        Object value;
        if (props != null) {
            for(Enumeration en = props.propertyNames(); en.hasMoreElements(); map.put(key, value)) {
                key = (String)en.nextElement();
                value = props.get(key);
                if (value == null) {
                    value = props.getProperty(key);
                }
            }
        }

    }

    @Nullable
    public static <E> E findFirstMatch(Set<?> source, Collection<E> candidates) {
        if (!isEmpty(source) && !isEmpty(candidates)) {
            Iterator var2 = candidates.iterator();

            Object candidate;
            do {
                if (!var2.hasNext()) {
                    return null;
                }

                candidate = var2.next();
            } while(!source.contains(candidate));

            return (E) candidate;
        } else {
            return null;
        }
    }
}
