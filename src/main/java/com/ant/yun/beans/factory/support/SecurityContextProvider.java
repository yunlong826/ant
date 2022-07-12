package com.ant.yun.beans.factory.support;

import java.security.AccessControlContext;

/**
 * @author jack_yun
 * @version 1.0
 * @description: TODO
 * @date 2022/7/12 20:44
 */
public interface SecurityContextProvider {
    AccessControlContext getAccessControlContext();
}
