package com.ant.yun.beans.factory.config;

import com.ant.yun.beans.BeansException;
import com.ant.yun.lang.Nullable;

/**
 * @author jack_yun
 * @version 1.0
 * @description: TODO
 * @date 2022/7/12 20:25
 */
public interface BeanExpressionResolver {
    @Nullable
    Object evaluate(@Nullable String var1, BeanExpressionContext var2) throws BeansException;
}
