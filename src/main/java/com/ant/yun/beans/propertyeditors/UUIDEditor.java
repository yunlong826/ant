package com.ant.yun.beans.propertyeditors;

import com.ant.yun.util.StringUtils;

import java.beans.PropertyEditorSupport;
import java.util.UUID;

/**
 * @author jack_yun
 * @version 1.0
 * @description: TODO
 * @date 2022/7/12 21:00
 */
public class UUIDEditor extends PropertyEditorSupport {
    public UUIDEditor() {
    }

    public void setAsText(String text) throws IllegalArgumentException {
        if (StringUtils.hasText(text)) {
            this.setValue(UUID.fromString(text));
        } else {
            this.setValue((Object)null);
        }

    }

    public String getAsText() {
        UUID value = (UUID)this.getValue();
        return value != null ? value.toString() : "";
    }
}

