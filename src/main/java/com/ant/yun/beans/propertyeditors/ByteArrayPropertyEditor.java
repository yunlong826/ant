package com.ant.yun.beans.propertyeditors;

import com.ant.yun.lang.Nullable;

import java.beans.PropertyEditorSupport;

/**
 * @author jack_yun
 * @version 1.0
 * @description: TODO
 * @date 2022/7/12 21:02
 */
public class ByteArrayPropertyEditor extends PropertyEditorSupport {
    public ByteArrayPropertyEditor() {
    }

    public void setAsText(@Nullable String text) {
        this.setValue(text != null ? text.getBytes() : null);
    }

    public String getAsText() {
        byte[] value = (byte[])((byte[])this.getValue());
        return value != null ? new String(value) : "";
    }
}