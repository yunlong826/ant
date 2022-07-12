package com.ant.yun.beans.factory.config;

import com.ant.yun.beans.BeansException;

/**
 * @author jack_yun
 * @version 1.0
 * @description: TODO
 * @date 2022/7/12 21:10
 */
public interface DestructionAwareBeanPostProcessor extends BeanPostProcessor {
    void postProcessBeforeDestruction(Object var1, String var2) throws BeansException;

    default boolean requiresDestruction(Object bean) {
        return true;
    }
}