package com.ant.yun.beans.factory;

import com.ant.yun.core.ResolvableType;
import com.ant.yun.lang.Nullable;
import com.ant.yun.util.StringUtils;

import java.util.Arrays;
import java.util.Collection;

/**
 * @author jack_yun
 * @version 1.0
 * @description: TODO
 * @date 2022/7/12 20:39
 */
public class NoUniqueBeanDefinitionException extends NoSuchBeanDefinitionException{
    private final int numberOfBeansFound;
    @Nullable
    private final Collection<String> beanNamesFound;

    public NoUniqueBeanDefinitionException(Class<?> type, int numberOfBeansFound, String message) {
        super(type, message);
        this.numberOfBeansFound = numberOfBeansFound;
        this.beanNamesFound = null;
    }

    public NoUniqueBeanDefinitionException(Class<?> type, Collection<String> beanNamesFound) {
        super(type, "expected single matching bean but found " + beanNamesFound.size() + ": " + StringUtils.collectionToCommaDelimitedString(beanNamesFound));
        this.numberOfBeansFound = beanNamesFound.size();
        this.beanNamesFound = beanNamesFound;
    }

    public NoUniqueBeanDefinitionException(Class<?> type, String... beanNamesFound) {
        this((Class)type, (Collection) Arrays.asList(beanNamesFound));
    }

    public NoUniqueBeanDefinitionException(ResolvableType type, Collection<String> beanNamesFound) {
        super(type, "expected single matching bean but found " + beanNamesFound.size() + ": " + StringUtils.collectionToCommaDelimitedString(beanNamesFound));
        this.numberOfBeansFound = beanNamesFound.size();
        this.beanNamesFound = beanNamesFound;
    }

    public NoUniqueBeanDefinitionException(ResolvableType type, String... beanNamesFound) {
        this((ResolvableType)type, (Collection)Arrays.asList(beanNamesFound));
    }

    public int getNumberOfBeansFound() {
        return this.numberOfBeansFound;
    }

    @Nullable
    public Collection<String> getBeanNamesFound() {
        return this.beanNamesFound;
    }
}
