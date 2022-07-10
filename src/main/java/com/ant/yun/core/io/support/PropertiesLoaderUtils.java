package com.ant.yun.core.io.support;

import com.ant.yun.lang.Nullable;
import com.ant.yun.util.Assert;
import com.ant.yun.util.ClassUtils;
import com.ant.yun.util.ResourceUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Enumeration;
import java.util.Properties;

/**
 * @author jack_yun
 * @version 1.0
 * @description: TODO
 * @date 2022/7/9 16:05
 */
public abstract class PropertiesLoaderUtils {

    public static Properties loadAllProperties(String resourceName, @Nullable ClassLoader classLoader) throws IOException {
        Assert.notNull(resourceName, "Resource name must not be null");
        ClassLoader classLoaderToUse = classLoader;
        if (classLoader == null) {
            classLoaderToUse = ClassUtils.getDefaultClassLoader();
        }

        Enumeration<URL> urls = classLoaderToUse != null ? classLoaderToUse.getResources(resourceName) : ClassLoader.getSystemResources(resourceName);
        Properties props = new Properties();

        while(urls.hasMoreElements()) {
            URL url = (URL)urls.nextElement();
            URLConnection con = url.openConnection();
            ResourceUtils.useCachesIfNecessary(con);
            InputStream is = con.getInputStream();
            Throwable var8 = null;

            try {
                if (resourceName.endsWith(".xml")) {
                    props.loadFromXML(is);
                } else {
                    props.load(is);
                }
            } catch (Throwable var17) {
                var8 = var17;
                throw var17;
            } finally {
                if (is != null) {
                    if (var8 != null) {
                        try {
                            is.close();
                        } catch (Throwable var16) {
                            var8.addSuppressed(var16);
                        }
                    } else {
                        is.close();
                    }
                }

            }
        }

        return props;
    }
}
