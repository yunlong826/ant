package com.ant.yun.beans.factory.parsing;

import com.ant.yun.util.Assert;

/**
 * @author jack_yun
 * @version 1.0
 * @description: TODO
 * @date 2022/7/9 16:37
 */
public class ConstructorArgumentEntry implements ParseState.Entry {
    private final int index;

    public ConstructorArgumentEntry() {
        this.index = -1;
    }

    public ConstructorArgumentEntry(int index) {
        Assert.isTrue(index >= 0, "Constructor argument index must be greater than or equal to zero");
        this.index = index;
    }

    public String toString() {
        return "Constructor-arg" + (this.index >= 0 ? " #" + this.index : "");
    }
}
