package com.ant.yun.beans.factory;

import com.ant.yun.util.Assert;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author jack_yun
 * @version 1.0
 * @description: TODO
 * @date 2022/7/9 15:49
 */
public abstract class BeanFactoryUtils {
    private static final Map<String, String> transformedBeanNameCache = new ConcurrentHashMap();

    public static String transformedBeanName(String name) {
        Assert.notNull(name, "'name' must not be null");
        return !name.startsWith("&") ? name : (String)transformedBeanNameCache.computeIfAbsent(name, (beanName) -> {
            do {
                beanName = beanName.substring("&".length());
            } while(beanName.startsWith("&"));

            return beanName;
        });
    }
}
