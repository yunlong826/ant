package com.ant.yun.beans;

import com.ant.yun.lang.Nullable;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * @author jack_yun
 * @version 1.0
 * @description: TODO
 * @date 2022/7/9 13:48
 */
public interface PropertyValues extends Iterable<PropertyValue>{
    default Iterator<PropertyValue> iterator() {
        return Arrays.asList(this.getPropertyValues()).iterator();
    }

    default Spliterator<PropertyValue> spliterator() {
        return Spliterators.spliterator(this.getPropertyValues(), 0);
    }

    default Stream<PropertyValue> stream() {
        return StreamSupport.stream(this.spliterator(), false);
    }

    PropertyValue[] getPropertyValues();

    @Nullable
    PropertyValue getPropertyValue(String var1);

    PropertyValues changesSince(PropertyValues var1);

    boolean contains(String var1);

    boolean isEmpty();
}
