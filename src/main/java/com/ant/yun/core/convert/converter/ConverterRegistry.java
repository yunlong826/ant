package com.ant.yun.core.convert.converter;

/**
 * @author jack_yun
 * @version 1.0
 * @description: TODO
 * @date 2022/7/9 14:16
 */
public interface ConverterRegistry {
    void addConverter(Converter<?, ?> var1);

    <S, T> void addConverter(Class<S> var1, Class<T> var2, Converter<? super S, ? extends T> var3);

    void addConverter(GenericConverter var1);

    void addConverterFactory(ConverterFactory<?, ?> var1);

    void removeConvertible(Class<?> var1, Class<?> var2);
}
