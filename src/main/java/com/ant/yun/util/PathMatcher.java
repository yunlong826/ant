package com.ant.yun.util;

import java.util.Comparator;
import java.util.Map;

/**
 * @author jack_yun
 * @version 1.0
 * @description: TODO
 * @date 2022/7/10 21:36
 */
public interface PathMatcher {
    boolean isPattern(String var1);

    boolean match(String var1, String var2);

    boolean matchStart(String var1, String var2);

    String extractPathWithinPattern(String var1, String var2);

    Map<String, String> extractUriTemplateVariables(String var1, String var2);

    Comparator<String> getPatternComparator(String var1);

    String combine(String var1, String var2);
}
