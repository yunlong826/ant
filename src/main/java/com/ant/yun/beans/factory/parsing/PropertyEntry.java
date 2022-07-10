package com.ant.yun.beans.factory.parsing;

import com.ant.yun.util.StringUtils;

/**
 * @author jack_yun
 * @version 1.0
 * @description: TODO
 * @date 2022/7/9 16:38
 */
public class PropertyEntry implements ParseState.Entry {
    private final String name;

    public PropertyEntry(String name) {
        if (!StringUtils.hasText(name)) {
            throw new IllegalArgumentException("Invalid property name '" + name + "'");
        } else {
            this.name = name;
        }
    }

    public String toString() {
        return "Property '" + this.name + "'";
    }
}
