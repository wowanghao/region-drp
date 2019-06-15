package com.jiajiayue.all.regiondrp.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface LogMe {

    String description() default "未定义任务描述";

    String compareTo() default "";

    String deleting() default "";

    boolean ignore() default false;

    String module() default "";
}
