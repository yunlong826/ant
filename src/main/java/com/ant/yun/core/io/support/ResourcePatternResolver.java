package com.ant.yun.core.io.support;

import com.ant.yun.core.io.Resource;
import com.ant.yun.core.io.ResourceLoader;

import java.io.IOException;

/**
 * @author jack_yun
 * @version 1.0
 * @description: TODO
 * @date 2022/7/9 14:11
 */
public interface ResourcePatternResolver extends ResourceLoader {
    String CLASSPATH_ALL_URL_PREFIX = "classpath*:";

    Resource[] getResources(String var1) throws IOException;
}
