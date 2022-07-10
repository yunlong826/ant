package com.ant.yun.beans.factory.support;

import com.ant.yun.beans.BeanMetadataElement;
import com.ant.yun.beans.Mergeable;
import com.ant.yun.lang.Nullable;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author jack_yun
 * @version 1.0
 * @description: TODO
 * @date 2022/7/9 16:47
 */
public class ManagedMap<K, V> extends LinkedHashMap<K, V> implements Mergeable, BeanMetadataElement {
    @Nullable
    private Object source;
    @Nullable
    private String keyTypeName;
    @Nullable
    private String valueTypeName;
    private boolean mergeEnabled;

    public ManagedMap() {
    }

    public ManagedMap(int initialCapacity) {
        super(initialCapacity);
    }

    public void setSource(@Nullable Object source) {
        this.source = source;
    }

    @Nullable
    public Object getSource() {
        return this.source;
    }

    public void setKeyTypeName(@Nullable String keyTypeName) {
        this.keyTypeName = keyTypeName;
    }

    @Nullable
    public String getKeyTypeName() {
        return this.keyTypeName;
    }

    public void setValueTypeName(@Nullable String valueTypeName) {
        this.valueTypeName = valueTypeName;
    }

    @Nullable
    public String getValueTypeName() {
        return this.valueTypeName;
    }

    public void setMergeEnabled(boolean mergeEnabled) {
        this.mergeEnabled = mergeEnabled;
    }

    public boolean isMergeEnabled() {
        return this.mergeEnabled;
    }

    public Object merge(@Nullable Object parent) {
        if (!this.mergeEnabled) {
            throw new IllegalStateException("Not allowed to merge when the 'mergeEnabled' property is set to 'false'");
        } else if (parent == null) {
            return this;
        } else if (!(parent instanceof Map)) {
            throw new IllegalArgumentException("Cannot merge with object of type [" + parent.getClass() + "]");
        } else {
            Map<K, V> merged = new ManagedMap();
            merged.putAll((Map)parent);
            merged.putAll(this);
            return merged;
        }
    }
}
