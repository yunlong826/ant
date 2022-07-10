package com.ant.yun.core.io;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author jack_yun
 * @version 1.0
 * @description: TODO
 * @date 2022/7/9 10:23
 */
public interface InputStreamSource {
    InputStream getInputStream() throws IOException;
}
