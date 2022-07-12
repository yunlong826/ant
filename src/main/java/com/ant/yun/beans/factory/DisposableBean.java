package com.ant.yun.beans.factory;

/**
 * @author jack_yun
 * @version 1.0
 * @description: TODO
 * @date 2022/7/12 20:22
 */
public interface DisposableBean {
    void destroy() throws Exception;
}
