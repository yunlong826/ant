package com.ant.yun.beans.factory.parsing;

/**
 * @author jack_yun
 * @version 1.0
 * @description: TODO
 * @date 2022/7/9 15:34
 */
public interface ProblemReporter {
    void fatal(Problem var1);

    void error(Problem var1);

    void warning(Problem var1);
}
