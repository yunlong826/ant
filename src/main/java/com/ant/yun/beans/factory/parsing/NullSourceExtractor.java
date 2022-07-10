package com.ant.yun.beans.factory.parsing;

import com.ant.yun.core.io.Resource;
import com.ant.yun.lang.Nullable;

/**
 * @author jack_yun
 * @version 1.0
 * @description: TODO
 * @date 2022/7/9 15:55
 */
public class NullSourceExtractor implements SourceExtractor {
    public NullSourceExtractor() {
    }

    @Nullable
    public Object extractSource(Object sourceCandidate, @Nullable Resource definitionResource) {
        return null;
    }
}
