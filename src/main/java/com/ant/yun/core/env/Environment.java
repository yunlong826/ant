package com.ant.yun.core.env;

/**
 * @author jack_yun
 * @version 1.0
 * @description: TODO
 * @date 2022/7/9 14:02
 */
public interface Environment extends PropertyResolver{

    String[] getActiveProfiles();

    String[] getDefaultProfiles();

    /** @deprecated */
    @Deprecated
    boolean acceptsProfiles(String... var1);

    boolean acceptsProfiles(Profiles var1);
}
