package com.ant.yun.beans.propertyeditors;

import com.ant.yun.lang.Nullable;
import com.ant.yun.util.NumberUtils;
import com.ant.yun.util.StringUtils;

import java.beans.PropertyEditorSupport;
import java.text.NumberFormat;

/**
 * @author jack_yun
 * @version 1.0
 * @description: TODO
 * @date 2022/7/12 21:04
 */
public class CustomNumberEditor extends PropertyEditorSupport {
    private final Class<? extends Number> numberClass;
    @Nullable
    private final NumberFormat numberFormat;
    private final boolean allowEmpty;

    public CustomNumberEditor(Class<? extends Number> numberClass, boolean allowEmpty) throws IllegalArgumentException {
        this(numberClass, (NumberFormat)null, allowEmpty);
    }

    public CustomNumberEditor(Class<? extends Number> numberClass, @Nullable NumberFormat numberFormat, boolean allowEmpty) throws IllegalArgumentException {
        if (!Number.class.isAssignableFrom(numberClass)) {
            throw new IllegalArgumentException("Property class must be a subclass of Number");
        } else {
            this.numberClass = numberClass;
            this.numberFormat = numberFormat;
            this.allowEmpty = allowEmpty;
        }
    }

    public void setAsText(String text) throws IllegalArgumentException {
        if (this.allowEmpty && !StringUtils.hasText(text)) {
            this.setValue((Object)null);
        } else if (this.numberFormat != null) {
            this.setValue(NumberUtils.parseNumber(text, this.numberClass, this.numberFormat));
        } else {
            this.setValue(NumberUtils.parseNumber(text, this.numberClass));
        }

    }

    public void setValue(@Nullable Object value) {
        if (value instanceof Number) {
            super.setValue(NumberUtils.convertNumberToTargetClass((Number)value, this.numberClass));
        } else {
            super.setValue(value);
        }

    }

    public String getAsText() {
        Object value = this.getValue();
        if (value == null) {
            return "";
        } else {
            return this.numberFormat != null ? this.numberFormat.format(value) : value.toString();
        }
    }
}

