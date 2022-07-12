package com.ant.yun.beans.propertyeditors;

import com.ant.yun.lang.Nullable;
import com.ant.yun.util.Assert;
import com.ant.yun.util.ReflectionUtils;

import java.beans.PropertyEditorSupport;
import java.lang.reflect.Array;
import java.util.*;

/**
 * @author jack_yun
 * @version 1.0
 * @description: TODO
 * @date 2022/7/12 21:01
 */
public class CustomCollectionEditor extends PropertyEditorSupport {
    private final Class<? extends Collection> collectionType;
    private final boolean nullAsEmptyCollection;

    public CustomCollectionEditor(Class<? extends Collection> collectionType) {
        this(collectionType, false);
    }

    public CustomCollectionEditor(Class<? extends Collection> collectionType, boolean nullAsEmptyCollection) {
        Assert.notNull(collectionType, "Collection type is required");
        if (!Collection.class.isAssignableFrom(collectionType)) {
            throw new IllegalArgumentException("Collection type [" + collectionType.getName() + "] does not implement [java.util.Collection]");
        } else {
            this.collectionType = collectionType;
            this.nullAsEmptyCollection = nullAsEmptyCollection;
        }
    }

    public void setAsText(String text) throws IllegalArgumentException {
        this.setValue(text);
    }

    public void setValue(@Nullable Object value) {
        if (value == null && this.nullAsEmptyCollection) {
            super.setValue(this.createCollection(this.collectionType, 0));
        } else if (value == null || this.collectionType.isInstance(value) && !this.alwaysCreateNewCollection()) {
            super.setValue(value);
        } else {
            Collection target;
//            Collection target;
            if (value instanceof Collection) {
                target = (Collection)value;
                target = this.createCollection(this.collectionType, target.size());
                Iterator var4 = target.iterator();

                while(var4.hasNext()) {
                    Object elem = var4.next();
                    target.add(this.convertElement(elem));
                }

                super.setValue(target);
            } else if (value.getClass().isArray()) {
                int length = Array.getLength(value);
                target = this.createCollection(this.collectionType, length);

                for(int i = 0; i < length; ++i) {
                    target.add(this.convertElement(Array.get(value, i)));
                }

                super.setValue(target);
            } else {
                target = this.createCollection(this.collectionType, 1);
                target.add(this.convertElement(value));
                super.setValue(target);
            }
        }

    }

    protected Collection<Object> createCollection(Class<? extends Collection> collectionType, int initialCapacity) {
        if (!collectionType.isInterface()) {
            try {
                return (Collection) ReflectionUtils.accessibleConstructor(collectionType, new Class[0]).newInstance();
            } catch (Throwable var4) {
                throw new IllegalArgumentException("Could not instantiate collection class: " + collectionType.getName(), var4);
            }
        } else if (List.class == collectionType) {
            return new ArrayList(initialCapacity);
        } else {
            return (Collection)(SortedSet.class == collectionType ? new TreeSet() : new LinkedHashSet(initialCapacity));
        }
    }

    protected boolean alwaysCreateNewCollection() {
        return false;
    }

    protected Object convertElement(Object element) {
        return element;
    }

    @Nullable
    public String getAsText() {
        return null;
    }
}

