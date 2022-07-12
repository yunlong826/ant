package com.ant.yun.beans.factory;

/**
 * @author jack_yun
 * @version 1.0
 * @description: TODO
 * @date 2022/7/12 20:20
 */
public class BeanCreationNotAllowedException extends BeanCreationException{
    public BeanCreationNotAllowedException(String beanName, String msg) {
        super(beanName, msg);
    }
}
