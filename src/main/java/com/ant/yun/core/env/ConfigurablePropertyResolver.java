package com.ant.yun.core.env;

import com.ant.yun.core.convert.support.ConfigurableConversionService;
import com.ant.yun.lang.Nullable;

/**
 * @author jack_yun
 * @version 1.0
 * @description: TODO
 * @date 2022/7/9 14:14
 */
public interface ConfigurablePropertyResolver extends PropertyResolver{
    ConfigurableConversionService getConversionService();

    void setConversionService(ConfigurableConversionService var1);

    void setPlaceholderPrefix(String var1);

    void setPlaceholderSuffix(String var1);

    void setValueSeparator(@Nullable String var1);

    void setIgnoreUnresolvableNestedPlaceholders(boolean var1);

    void setRequiredProperties(String... var1);

    void validateRequiredProperties() throws MissingRequiredPropertiesException;
}
