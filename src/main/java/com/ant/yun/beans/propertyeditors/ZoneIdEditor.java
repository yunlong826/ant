package com.ant.yun.beans.propertyeditors;

import java.beans.PropertyEditorSupport;
import java.time.ZoneId;

/**
 * @author jack_yun
 * @version 1.0
 * @description: TODO
 * @date 2022/7/12 21:00
 */
public class ZoneIdEditor extends PropertyEditorSupport {
    public ZoneIdEditor() {
    }

    public void setAsText(String text) throws IllegalArgumentException {
        this.setValue(ZoneId.of(text));
    }

    public String getAsText() {
        ZoneId value = (ZoneId)this.getValue();
        return value != null ? value.getId() : "";
    }
}
