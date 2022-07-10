package com.ant.yun.beans.factory.support;

import com.ant.yun.lang.Nullable;
import com.ant.yun.util.StringUtils;

/**
 * @author jack_yun
 * @version 1.0
 * @description: TODO
 * @date 2022/7/9 16:23
 */
public class BeanDefinitionDefaults {
    @Nullable
    private Boolean lazyInit;
    private int autowireMode = 0;
    private int dependencyCheck = 0;
    @Nullable
    private String initMethodName;
    @Nullable
    private String destroyMethodName;

    public BeanDefinitionDefaults() {
    }

    public void setLazyInit(boolean lazyInit) {
        this.lazyInit = lazyInit;
    }

    public boolean isLazyInit() {
        return this.lazyInit != null && this.lazyInit;
    }

    @Nullable
    public Boolean getLazyInit() {
        return this.lazyInit;
    }

    public void setAutowireMode(int autowireMode) {
        this.autowireMode = autowireMode;
    }

    public int getAutowireMode() {
        return this.autowireMode;
    }

    public void setDependencyCheck(int dependencyCheck) {
        this.dependencyCheck = dependencyCheck;
    }

    public int getDependencyCheck() {
        return this.dependencyCheck;
    }

    public void setInitMethodName(@Nullable String initMethodName) {
        this.initMethodName = StringUtils.hasText(initMethodName) ? initMethodName : null;
    }

    @Nullable
    public String getInitMethodName() {
        return this.initMethodName;
    }

    public void setDestroyMethodName(@Nullable String destroyMethodName) {
        this.destroyMethodName = StringUtils.hasText(destroyMethodName) ? destroyMethodName : null;
    }

    @Nullable
    public String getDestroyMethodName() {
        return this.destroyMethodName;
    }
}
