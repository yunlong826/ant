package com.ant.yun.beans;

import com.ant.yun.lang.Nullable;

/**
 * @author jack_yun
 * @version 1.0
 * @description: TODO
 * @date 2022/7/9 13:57
 */
public interface Mergeable {
    boolean isMergeEnabled();

    Object merge(@Nullable Object var1);
}
