package com.ant.yun.beans.factory.xml;

import com.ant.yun.beans.factory.config.BeanDefinition;
import com.ant.yun.beans.factory.config.BeanDefinitionHolder;
import com.ant.yun.lang.Nullable;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * @author jack_yun
 * @version 1.0
 * @description: TODO
 * @date 2022/7/9 15:42
 */
public interface NamespaceHandler {
    void init();

    @Nullable
    BeanDefinition parse(Element var1, ParserContext var2);

    @Nullable
    BeanDefinitionHolder decorate(Node var1, BeanDefinitionHolder var2, ParserContext var3);
}
