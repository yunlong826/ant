package com.ant.yun.beans;

import com.ant.yun.core.convert.ConversionService;
import com.ant.yun.lang.Nullable;
import com.sun.xml.internal.ws.spi.db.PropertyAccessor;

/**
 * @author jack_yun
 * @version 1.0
 * @description: TODO
 * @date 2022/7/12 21:11
 */
public interface ConfigurablePropertyAccessor extends PropertyAccessor, PropertyEditorRegistry, TypeConverter {
    void setConversionService(@Nullable ConversionService var1);

    @Nullable
    ConversionService getConversionService();

    void setExtractOldValueForEditor(boolean var1);

    boolean isExtractOldValueForEditor();

    void setAutoGrowNestedPaths(boolean var1);

    boolean isAutoGrowNestedPaths();
}