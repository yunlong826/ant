package com.ant.yun.beans.factory.parsing;

import com.ant.yun.beans.factory.BeanDefinitionStoreException;

/**
 * @author jack_yun
 * @version 1.0
 * @description: TODO
 * @date 2022/7/9 15:54
 */
public class BeanDefinitionParsingException extends BeanDefinitionStoreException {
    public BeanDefinitionParsingException(Problem problem) {
        super(problem.getResourceDescription(), problem.toString(), problem.getRootCause());
    }
}
