package com.ant.yun.beans.factory.xml;

import com.ant.yun.core.io.Resource;
import com.ant.yun.core.io.ResourceLoader;
import com.ant.yun.lang.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;

/**
 * @author jack_yun
 * @version 1.0
 * @description: TODO 通过 ResourceLoader 来解析实体的引用
 * @date 2022/7/9 16:02
 */
public class ResourceEntityResolver extends DelegatingEntityResolver {
    private static final Logger logger = LoggerFactory.getLogger(ResourceEntityResolver.class);
    private final ResourceLoader resourceLoader;

    public ResourceEntityResolver(ResourceLoader resourceLoader) {
        super(resourceLoader.getClassLoader());
        this.resourceLoader = resourceLoader;
    }

    @Nullable
    public InputSource resolveEntity(@Nullable String publicId, @Nullable String systemId) throws SAXException, IOException {
        InputSource source = super.resolveEntity(publicId, systemId);
        if (source == null && systemId != null) {
            String resourcePath = null;

            String url;
            try {
                url = URLDecoder.decode(systemId, "UTF-8");
                String givenUrl = (new URL(url)).toString();
                String systemRootUrl = (new File("")).toURI().toURL().toString();
                if (givenUrl.startsWith(systemRootUrl)) {
                    resourcePath = givenUrl.substring(systemRootUrl.length());
                }
            } catch (Exception var9) {
                if (logger.isDebugEnabled()) {
                    logger.debug("Could not resolve XML entity [" + systemId + "] against system root URL", var9);
                }

                resourcePath = systemId;
            }

            if (resourcePath != null) {
                if (logger.isTraceEnabled()) {
                    logger.trace("Trying to locate XML entity [" + systemId + "] as resource [" + resourcePath + "]");
                }

                Resource resource = this.resourceLoader.getResource(resourcePath);
                source = new InputSource(resource.getInputStream());
                source.setPublicId(publicId);
                source.setSystemId(systemId);
                if (logger.isDebugEnabled()) {
                    logger.debug("Found XML entity [" + systemId + "]: " + resource);
                }
            } else if (systemId.endsWith(".dtd") || systemId.endsWith(".xsd")) {
                url = systemId;
                if (systemId.startsWith("http:")) {
                    url = "https:" + systemId.substring(5);
                }

                try {
                    source = new InputSource((new URL(url)).openStream());
                    source.setPublicId(publicId);
                    source.setSystemId(systemId);
                } catch (IOException var8) {
                    if (logger.isDebugEnabled()) {
                        logger.debug("Could not resolve XML entity [" + systemId + "] through URL [" + url + "]", var8);
                    }

                    source = null;
                }
            }
        }

        return source;
    }
}
