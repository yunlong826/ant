package com.ant.yun.beans.propertyeditors;

import java.beans.PropertyEditorSupport;
import java.util.Currency;

/**
 * @author jack_yun
 * @version 1.0
 * @description: TODO
 * @date 2022/7/12 20:50
 */
public class CurrencyEditor extends PropertyEditorSupport {
    public CurrencyEditor() {
    }

    public void setAsText(String text) throws IllegalArgumentException {
        this.setValue(Currency.getInstance(text));
    }

    public String getAsText() {
        Currency value = (Currency)this.getValue();
        return value != null ? value.getCurrencyCode() : "";
    }
}
