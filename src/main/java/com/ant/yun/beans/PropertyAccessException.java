package com.ant.yun.beans;

import com.ant.yun.lang.Nullable;

import java.beans.PropertyChangeEvent;

/**
 * @author jack_yun
 * @version 1.0
 * @description: TODO
 * @date 2022/7/12 21:12
 */
public abstract class PropertyAccessException extends BeansException {
    @Nullable
    private final PropertyChangeEvent propertyChangeEvent;

    public PropertyAccessException(PropertyChangeEvent propertyChangeEvent, String msg, @Nullable Throwable cause) {
        super(msg, cause);
        this.propertyChangeEvent = propertyChangeEvent;
    }

    public PropertyAccessException(String msg, @Nullable Throwable cause) {
        super(msg, cause);
        this.propertyChangeEvent = null;
    }

    @Nullable
    public PropertyChangeEvent getPropertyChangeEvent() {
        return this.propertyChangeEvent;
    }

    @Nullable
    public String getPropertyName() {
        return this.propertyChangeEvent != null ? this.propertyChangeEvent.getPropertyName() : null;
    }

    @Nullable
    public Object getValue() {
        return this.propertyChangeEvent != null ? this.propertyChangeEvent.getNewValue() : null;
    }

    public abstract String getErrorCode();
}

