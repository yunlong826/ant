package com.ant.yun.beans.factory.support;

import com.ant.yun.beans.BeanMetadataElement;
import com.ant.yun.beans.Mergeable;
import com.ant.yun.lang.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jack_yun
 * @version 1.0
 * @description: TODO
 * @date 2022/7/9 16:45
 */
public class ManagedList<E> extends ArrayList<E> implements Mergeable, BeanMetadataElement {
    @Nullable
    private Object source;
    @Nullable
    private String elementTypeName;
    private boolean mergeEnabled;

    public ManagedList() {
    }

    public ManagedList(int initialCapacity) {
        super(initialCapacity);
    }

    public void setSource(@Nullable Object source) {
        this.source = source;
    }

    @Nullable
    public Object getSource() {
        return this.source;
    }

    public void setElementTypeName(String elementTypeName) {
        this.elementTypeName = elementTypeName;
    }

    @Nullable
    public String getElementTypeName() {
        return this.elementTypeName;
    }

    public void setMergeEnabled(boolean mergeEnabled) {
        this.mergeEnabled = mergeEnabled;
    }

    public boolean isMergeEnabled() {
        return this.mergeEnabled;
    }

    public List<E> merge(@Nullable Object parent) {
        if (!this.mergeEnabled) {
            throw new IllegalStateException("Not allowed to merge when the 'mergeEnabled' property is set to 'false'");
        } else if (parent == null) {
            return this;
        } else if (!(parent instanceof List)) {
            throw new IllegalArgumentException("Cannot merge with object of type [" + parent.getClass() + "]");
        } else {
            List<E> merged = new ManagedList();
            merged.addAll((List)parent);
            merged.addAll(this);
            return merged;
        }
    }
}
