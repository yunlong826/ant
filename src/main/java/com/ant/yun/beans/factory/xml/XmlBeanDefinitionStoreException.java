package com.ant.yun.beans.factory.xml;

import com.ant.yun.beans.factory.BeanDefinitionStoreException;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
 * @author jack_yun
 * @version 1.0
 * @description: TODO
 * @date 2022/7/9 16:08
 */
public class XmlBeanDefinitionStoreException extends BeanDefinitionStoreException {
    public XmlBeanDefinitionStoreException(String resourceDescription, String msg, SAXException cause) {
        super(resourceDescription, msg, cause);
    }

    public int getLineNumber() {
        Throwable cause = this.getCause();
        return cause instanceof SAXParseException ? ((SAXParseException)cause).getLineNumber() : -1;
    }
}
