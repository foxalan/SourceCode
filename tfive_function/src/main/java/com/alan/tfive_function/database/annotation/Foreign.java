package com.alan.tfive_function.database.annotation;


import com.alan.tfive_function.database.table.Entity;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author  alan
 *  外键
 *  todo 完全不懂这个概念
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Foreign {
    Class<? extends Entity> value();
}
