package com.ant.yun.beans.factory.support;

import com.ant.yun.beans.FatalBeanException;

/**
 * @author jack_yun
 * @version 1.0
 * @description: TODO
 * @date 2022/7/9 16:29
 */
public class BeanDefinitionValidationException extends FatalBeanException {
    public BeanDefinitionValidationException(String msg) {
        super(msg);
    }

    public BeanDefinitionValidationException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
