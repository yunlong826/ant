package com.ant.yun.beans.propertyeditors;

import com.ant.yun.lang.Nullable;
import com.ant.yun.util.Assert;
import com.ant.yun.util.ReflectionUtils;

import java.beans.PropertyEditorSupport;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * @author jack_yun
 * @version 1.0
 * @description: TODO
 * @date 2022/7/12 21:02
 */
public class CustomMapEditor extends PropertyEditorSupport {
    private final Class<? extends Map> mapType;
    private final boolean nullAsEmptyMap;

    public CustomMapEditor(Class<? extends Map> mapType) {
        this(mapType, false);
    }

    public CustomMapEditor(Class<? extends Map> mapType, boolean nullAsEmptyMap) {
        Assert.notNull(mapType, "Map type is required");
        if (!Map.class.isAssignableFrom(mapType)) {
            throw new IllegalArgumentException("Map type [" + mapType.getName() + "] does not implement [java.util.Map]");
        } else {
            this.mapType = mapType;
            this.nullAsEmptyMap = nullAsEmptyMap;
        }
    }

    public void setAsText(String text) throws IllegalArgumentException {
        this.setValue(text);
    }

    public void setValue(@Nullable Object value) {
        if (value == null && this.nullAsEmptyMap) {
            super.setValue(this.createMap(this.mapType, 0));
        } else if (value == null || this.mapType.isInstance(value) && !this.alwaysCreateNewMap()) {
            super.setValue(value);
        } else {
            if (!(value instanceof Map)) {
                throw new IllegalArgumentException("Value cannot be converted to Map: " + value);
            }

            Map<?, ?> source = (Map)value;
            Map<Object, Object> target = this.createMap(this.mapType, source.size());
            source.forEach((key, val) -> {
                target.put(this.convertKey(key), this.convertValue(val));
            });
            super.setValue(target);
        }

    }

    protected Map<Object, Object> createMap(Class<? extends Map> mapType, int initialCapacity) {
        if (!mapType.isInterface()) {
            try {
                return (Map) ReflectionUtils.accessibleConstructor(mapType, new Class[0]).newInstance();
            } catch (Throwable var4) {
                throw new IllegalArgumentException("Could not instantiate map class: " + mapType.getName(), var4);
            }
        } else {
            return (Map)(SortedMap.class == mapType ? new TreeMap() : new LinkedHashMap(initialCapacity));
        }
    }

    protected boolean alwaysCreateNewMap() {
        return false;
    }

    protected Object convertKey(Object key) {
        return key;
    }

    protected Object convertValue(Object value) {
        return value;
    }

    @Nullable
    public String getAsText() {
        return null;
    }
}

