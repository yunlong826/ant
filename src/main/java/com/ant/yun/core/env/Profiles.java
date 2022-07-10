package com.ant.yun.core.env;

import java.util.function.Predicate;

/**
 * @author jack_yun
 * @version 1.0
 * @description: TODO
 * @date 2022/7/9 14:02
 */
@FunctionalInterface
public interface Profiles {
    boolean matches(Predicate<String> var1);

    static Profiles of(String... profiles) {
        return ProfilesParser.parse(profiles);
    }
}
