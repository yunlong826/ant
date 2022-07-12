package com.ant.yun.beans.propertyeditors;

import com.ant.yun.lang.Nullable;

import java.beans.PropertyEditorSupport;

/**
 * @author jack_yun
 * @version 1.0
 * @description: TODO
 * @date 2022/7/12 21:03
 */
public class CharArrayPropertyEditor extends PropertyEditorSupport {
    public CharArrayPropertyEditor() {
    }

    public void setAsText(@Nullable String text) {
        this.setValue(text != null ? text.toCharArray() : null);
    }

    public String getAsText() {
        char[] value = (char[])((char[])this.getValue());
        return value != null ? new String(value) : "";
    }
}
