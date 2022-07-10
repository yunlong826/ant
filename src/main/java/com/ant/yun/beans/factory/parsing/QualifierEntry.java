package com.ant.yun.beans.factory.parsing;

import com.ant.yun.util.StringUtils;

/**
 * @author jack_yun
 * @version 1.0
 * @description: TODO
 * @date 2022/7/9 16:42
 */
public class QualifierEntry implements ParseState.Entry {
    private final String typeName;

    public QualifierEntry(String typeName) {
        if (!StringUtils.hasText(typeName)) {
            throw new IllegalArgumentException("Invalid qualifier type '" + typeName + "'");
        } else {
            this.typeName = typeName;
        }
    }

    public String toString() {
        return "Qualifier '" + this.typeName + "'";
    }
}
