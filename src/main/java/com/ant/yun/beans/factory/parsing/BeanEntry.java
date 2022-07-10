package com.ant.yun.beans.factory.parsing;

/**
 * @author jack_yun
 * @version 1.0
 * @description: TODO
 * @date 2022/7/9 16:32
 */
public class BeanEntry implements ParseState.Entry {
    private final String beanDefinitionName;

    public BeanEntry(String beanDefinitionName) {
        this.beanDefinitionName = beanDefinitionName;
    }

    public String toString() {
        return "Bean '" + this.beanDefinitionName + "'";
    }
}
