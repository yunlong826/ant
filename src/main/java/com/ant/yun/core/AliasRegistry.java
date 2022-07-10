package com.ant.yun.core;

/**
 * @author jack_yun
 * @version 1.0
 * @description: TODO
 * @date 2022/7/9 13:20
 */
public interface AliasRegistry {
    void registerAlias(String var1, String var2);

    void removeAlias(String var1);

    boolean isAlias(String var1);

    String[] getAliases(String var1);
}
