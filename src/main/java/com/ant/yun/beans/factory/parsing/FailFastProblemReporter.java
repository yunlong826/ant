package com.ant.yun.beans.factory.parsing;

import com.ant.yun.lang.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author jack_yun
 * @version 1.0
 * @description: TODO
 * @date 2022/7/9 15:53
 */
public class FailFastProblemReporter implements ProblemReporter {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    public FailFastProblemReporter() {
    }

    public void setLogger(@Nullable Logger logger) {
        this.logger = logger != null ? logger : LoggerFactory.getLogger(this.getClass());
    }

    public void fatal(Problem problem) {
        throw new BeanDefinitionParsingException(problem);
    }

    public void error(Problem problem) {
        throw new BeanDefinitionParsingException(problem);
    }

    public void warning(Problem problem) {
        this.logger.warn(String.valueOf(problem), problem.getRootCause());
    }
}
