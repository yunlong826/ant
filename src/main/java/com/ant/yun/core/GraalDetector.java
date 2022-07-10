package com.ant.yun.core;

/**
 * @author jack_yun
 * @version 1.0
 * @description: TODO
 * @date 2022/7/9 13:28
 */
abstract class GraalDetector {
    private static final boolean imageCode = System.getProperty("org.graalvm.nativeimage.imagecode") != null;

    GraalDetector() {
    }

    public static boolean inImageCode() {
        return imageCode;
    }
}
