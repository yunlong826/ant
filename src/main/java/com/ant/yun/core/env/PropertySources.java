package com.ant.yun.core.env;

import com.ant.yun.lang.Nullable;

import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * @author jack_yun
 * @version 1.0
 * @description: TODO
 * @date 2022/7/9 14:20
 */
public interface PropertySources extends Iterable<PropertySource<?>>{
    default Stream<PropertySource<?>> stream() {
        return StreamSupport.stream(this.spliterator(), false);
    }

    boolean contains(String var1);

    @Nullable
    PropertySource<?> get(String var1);
}
