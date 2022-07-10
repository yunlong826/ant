package com.ant.yun.beans.factory.xml;

import org.w3c.dom.Document;
import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;

/**
 * @author jack_yun
 * @version 1.0
 * @description: TODO
 * @date 2022/7/9 15:56
 */
public interface DocumentLoader {
    Document loadDocument(InputSource var1,
                          EntityResolver var2,
                          ErrorHandler var3, int var4, boolean var5) throws Exception;
}
