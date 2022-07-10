package com.ant.yun.beans.factory.xml;

import com.ant.yun.beans.factory.BeanDefinitionStoreException;
import org.w3c.dom.Document;

/**
 * @author jack_yun
 * @version 1.0
 * @description: TODO
 * @date 2022/7/9 15:31
 */
public interface BeanDefinitionDocumentReader {
    void registerBeanDefinitions(Document var1, XmlReaderContext var2) throws BeanDefinitionStoreException;
}
