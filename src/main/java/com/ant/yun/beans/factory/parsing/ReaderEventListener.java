package com.ant.yun.beans.factory.parsing;

import java.util.EventListener;

/**
 * @author jack_yun
 * @version 1.0
 * @description: TODO
 * @date 2022/7/9 15:34
 */
public interface ReaderEventListener extends EventListener {
    void defaultsRegistered(DefaultsDefinition var1);

    void componentRegistered(ComponentDefinition var1);

    void aliasRegistered(AliasDefinition var1);

    void importProcessed(ImportDefinition var1);
}
