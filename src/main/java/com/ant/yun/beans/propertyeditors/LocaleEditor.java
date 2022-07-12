package com.ant.yun.beans.propertyeditors;

import com.ant.yun.util.StringUtils;

import java.beans.PropertyEditorSupport;

/**
 * @author jack_yun
 * @version 1.0
 * @description: TODO
 * @date 2022/7/12 20:53
 */
public class LocaleEditor extends PropertyEditorSupport {
    public LocaleEditor() {
    }

    public void setAsText(String text) {
        this.setValue(StringUtils.parseLocaleString(text));
    }

    public String getAsText() {
        Object value = this.getValue();
        return value != null ? value.toString() : "";
    }
}
