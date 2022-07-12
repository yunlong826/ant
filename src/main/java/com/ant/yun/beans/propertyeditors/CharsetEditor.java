package com.ant.yun.beans.propertyeditors;

import com.ant.yun.util.StringUtils;

import java.beans.PropertyEditorSupport;
import java.nio.charset.Charset;

/**
 * @author jack_yun
 * @version 1.0
 * @description: TODO
 * @date 2022/7/12 20:48
 */
public class CharsetEditor extends PropertyEditorSupport {
    public CharsetEditor() {
    }

    public void setAsText(String text) throws IllegalArgumentException {
        if (StringUtils.hasText(text)) {
            this.setValue(Charset.forName(text));
        } else {
            this.setValue((Object)null);
        }

    }

    public String getAsText() {
        Charset value = (Charset)this.getValue();
        return value != null ? value.name() : "";
    }
}
