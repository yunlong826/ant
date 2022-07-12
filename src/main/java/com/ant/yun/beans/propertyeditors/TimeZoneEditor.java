package com.ant.yun.beans.propertyeditors;

import com.ant.yun.util.StringUtils;

import java.beans.PropertyEditorSupport;
import java.util.TimeZone;

/**
 * @author jack_yun
 * @version 1.0
 * @description: TODO
 * @date 2022/7/12 20:58
 */
public class TimeZoneEditor extends PropertyEditorSupport {
    public TimeZoneEditor() {
    }

    public void setAsText(String text) throws IllegalArgumentException {
        this.setValue(StringUtils.parseTimeZoneString(text));
    }

    public String getAsText() {
        TimeZone value = (TimeZone)this.getValue();
        return value != null ? value.getID() : "";
    }
}