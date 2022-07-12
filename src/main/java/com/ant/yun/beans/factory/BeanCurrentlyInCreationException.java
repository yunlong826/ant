package com.ant.yun.beans.factory;

/**
 * @author jack_yun
 * @version 1.0
 * @description: TODO
 * @date 2022/7/12 20:21
 */
public class BeanCurrentlyInCreationException extends BeanCreationException{
    public BeanCurrentlyInCreationException(String beanName) {
        super(beanName, "Requested bean is currently in creation: Is there an unresolvable circular reference?");
    }

    public BeanCurrentlyInCreationException(String beanName, String msg) {
        super(beanName, msg);
    }
}
