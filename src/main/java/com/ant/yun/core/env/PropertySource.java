package com.ant.yun.core.env;

import com.ant.yun.lang.Nullable;
import com.ant.yun.util.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author jack_yun
 * @version 1.0
 * @description: TODO
 * @date 2022/7/9 14:21
 */
public class PropertySource<T> {
    protected final Logger logger;
    protected final String name;
    protected final T source;

    public PropertySource(String name, T source) {
        this.logger = LoggerFactory.getLogger(this.getClass());
        Assert.hasText(name, "Property source name must contain at least one character");
        Assert.notNull(source, "Property source must not be null");
        this.name = name;
        this.source = source;
    }

    public String getName() {
        return this.name;
    }

    public static PropertySource<?> named(String name) {
        return new PropertySource.ComparisonPropertySource(name);
    }

    static class ComparisonPropertySource extends PropertySource.StubPropertySource {
        private static final String USAGE_ERROR = "ComparisonPropertySource instances are for use with collection comparison only";

        public ComparisonPropertySource(String name) {
            super(name);
        }

        public Object getSource() {
            throw new UnsupportedOperationException("ComparisonPropertySource instances are for use with collection comparison only");
        }

        public boolean containsProperty(String name) {
            throw new UnsupportedOperationException("ComparisonPropertySource instances are for use with collection comparison only");
        }

        @Nullable
        public String getProperty(String name) {
            throw new UnsupportedOperationException("ComparisonPropertySource instances are for use with collection comparison only");
        }
    }
    public static class StubPropertySource extends PropertySource<Object> {
        public StubPropertySource(String name) {
            super(name, new Object());
        }

        @Nullable
        public String getProperty(String name) {
            return null;
        }
    }
}
