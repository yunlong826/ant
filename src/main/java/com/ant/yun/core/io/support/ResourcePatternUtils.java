package com.ant.yun.core.io.support;

import com.ant.yun.core.io.ResourceLoader;
import com.ant.yun.lang.Nullable;
import com.ant.yun.util.ResourceUtils;

/**
 * @author jack_yun
 * @version 1.0
 * @description: TODO
 * @date 2022/7/9 16:53
 */
public abstract class ResourcePatternUtils {
    public ResourcePatternUtils() {
    }

    public static boolean isUrl(@Nullable String resourceLocation) {
        return resourceLocation != null && (resourceLocation.startsWith("classpath*:") || ResourceUtils.isUrl(resourceLocation));
    }

    public static ResourcePatternResolver getResourcePatternResolver(@Nullable ResourceLoader resourceLoader) {
        if (resourceLoader instanceof ResourcePatternResolver) {
            return (ResourcePatternResolver)resourceLoader;
        } else {
            return resourceLoader != null ? new PathMatchingResourcePatternResolver(resourceLoader) : new PathMatchingResourcePatternResolver();
        }
    }
}
