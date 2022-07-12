package com.ant.yun.beans.factory;

import com.ant.yun.beans.FatalBeanException;

/**
 * @author jack_yun
 * @version 1.0
 * @description: TODO
 * @date 2022/7/12 20:36
 */
public class FactoryBeanNotInitializedException extends FatalBeanException {
    public FactoryBeanNotInitializedException() {
        super("FactoryBean is not fully initialized yet");
    }

    public FactoryBeanNotInitializedException(String msg) {
        super(msg);
    }
}
