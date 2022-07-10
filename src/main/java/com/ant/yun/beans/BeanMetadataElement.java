package com.ant.yun.beans;

import com.ant.yun.lang.Nullable;

/**
 * @author jack_yun
 * @version 1.0
 * @description: TODO
 * @date 2022/7/9 13:45
 */
public interface BeanMetadataElement {
    @Nullable
    default Object getSource() {
        return null;
    }
}
