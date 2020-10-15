package com.alan.tfive_function.database.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author alan
 * function: 表中的子项
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Column {

    String name() default "";


    boolean notnull() default false;

    boolean unique() default false;

    String aliasName() default "";

    String defVal() default "";

}
