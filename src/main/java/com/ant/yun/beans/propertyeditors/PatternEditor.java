package com.ant.yun.beans.propertyeditors;

import com.ant.yun.lang.Nullable;

import java.beans.PropertyEditorSupport;
import java.util.regex.Pattern;

/**
 * @author jack_yun
 * @version 1.0
 * @description: TODO
 * @date 2022/7/12 20:56
 */
public class PatternEditor extends PropertyEditorSupport {
    private final int flags;

    public PatternEditor() {
        this.flags = 0;
    }

    public PatternEditor(int flags) {
        this.flags = flags;
    }

    public void setAsText(@Nullable String text) {
        this.setValue(text != null ? Pattern.compile(text, this.flags) : null);
    }

    public String getAsText() {
        Pattern value = (Pattern)this.getValue();
        return value != null ? value.pattern() : "";
    }
}
