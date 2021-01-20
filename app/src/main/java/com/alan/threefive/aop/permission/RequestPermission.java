package com.alan.threefive.aop.permission;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author alan
 * function: 权限
 */
public @interface RequestPermission {

    int  value();
}
