package com.ant.yun.beans.factory.xml;

import com.ant.yun.beans.factory.BeanDefinitionStoreException;
import com.ant.yun.beans.factory.config.BeanDefinition;
import com.ant.yun.beans.factory.parsing.ProblemReporter;
import com.ant.yun.beans.factory.parsing.ReaderContext;
import com.ant.yun.beans.factory.parsing.ReaderEventListener;
import com.ant.yun.beans.factory.parsing.SourceExtractor;
import com.ant.yun.beans.factory.support.BeanDefinitionRegistry;
import com.ant.yun.core.env.Environment;
import com.ant.yun.core.io.Resource;
import com.ant.yun.core.io.ResourceLoader;
import com.ant.yun.lang.Nullable;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import java.io.StringReader;

/**
 * @author jack_yun
 * @version 1.0
 * @description: TODO
 * @date 2022/7/9 15:33
 */
public class XmlReaderContext extends ReaderContext {
    private final XmlBeanDefinitionReader reader;
    private final NamespaceHandlerResolver namespaceHandlerResolver;

    public XmlReaderContext(Resource resource, ProblemReporter problemReporter, ReaderEventListener eventListener, SourceExtractor sourceExtractor, XmlBeanDefinitionReader reader, NamespaceHandlerResolver namespaceHandlerResolver) {
        super(resource, problemReporter, eventListener, sourceExtractor);
        this.reader = reader;
        this.namespaceHandlerResolver = namespaceHandlerResolver;
    }

    public final XmlBeanDefinitionReader getReader() {
        return this.reader;
    }

    public final BeanDefinitionRegistry getRegistry() {
        return this.reader.getRegistry();
    }

    @Nullable
    public final ResourceLoader getResourceLoader() {
        return this.reader.getResourceLoader();
    }

    @Nullable
    public final ClassLoader getBeanClassLoader() {
        return this.reader.getBeanClassLoader();
    }

    public final Environment getEnvironment() {
        return this.reader.getEnvironment();
    }

    public final NamespaceHandlerResolver getNamespaceHandlerResolver() {
        return this.namespaceHandlerResolver;
    }

    public String generateBeanName(BeanDefinition beanDefinition) {
        return this.reader.getBeanNameGenerator().generateBeanName(beanDefinition, this.getRegistry());
    }

    public String registerWithGeneratedName(BeanDefinition beanDefinition) {
        String generatedName = this.generateBeanName(beanDefinition);
        this.getRegistry().registerBeanDefinition(generatedName, beanDefinition);
        return generatedName;
    }

    public Document readDocumentFromString(String documentContent) {
        InputSource is = new InputSource(new StringReader(documentContent));

        try {
            return this.reader.doLoadDocument(is, this.getResource());
        } catch (Exception var4) {
            throw new BeanDefinitionStoreException("Failed to read XML document", var4);
        }
    }
}
