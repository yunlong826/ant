package com.ant.yun.beans.factory.parsing;

import com.ant.yun.lang.Nullable;
import com.ant.yun.util.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jack_yun
 * @version 1.0
 * @description: TODO
 * @date 2022/7/9 15:44
 */
public class CompositeComponentDefinition extends AbstractComponentDefinition{
    private final String name;
    @Nullable
    private final Object source;
    private final List<ComponentDefinition> nestedComponents = new ArrayList();

    public CompositeComponentDefinition(String name, @Nullable Object source) {
        Assert.notNull(name, "Name must not be null");
        this.name = name;
        this.source = source;
    }

    public String getName() {
        return this.name;
    }

    @Nullable
    public Object getSource() {
        return this.source;
    }

    public void addNestedComponent(ComponentDefinition component) {
        Assert.notNull(component, "ComponentDefinition must not be null");
        this.nestedComponents.add(component);
    }

    public ComponentDefinition[] getNestedComponents() {
        return (ComponentDefinition[])this.nestedComponents.toArray(new ComponentDefinition[0]);
    }
}
