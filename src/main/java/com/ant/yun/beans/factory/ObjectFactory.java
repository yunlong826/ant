package com.ant.yun.beans.factory;

import com.ant.yun.beans.BeansException;

/**
 * @author jack_yun
 * @version 1.0
 * @description: TODO
 * @date 2022/7/12 20:19
 */
@FunctionalInterface
public interface ObjectFactory<T>{
    T getObject() throws BeansException;
}
