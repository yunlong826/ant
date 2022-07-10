package com.ant.yun.core.env;

import java.util.Map;

/**
 * @author jack_yun
 * @version 1.0
 * @description: TODO
 * @date 2022/7/9 14:14
 */
public interface ConfigurableEnvironment extends Environment, ConfigurablePropertyResolver{
    void setActiveProfiles(String... var1);

    void addActiveProfile(String var1);

    void setDefaultProfiles(String... var1);

    MutablePropertySources getPropertySources();

    Map<String, Object> getSystemProperties();

    Map<String, Object> getSystemEnvironment();

    void merge(ConfigurableEnvironment var1);
}
