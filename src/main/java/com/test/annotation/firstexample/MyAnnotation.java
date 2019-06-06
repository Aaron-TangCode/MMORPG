package com.test.annotation.firstexample;

/**
 * @ClassName MyAnnotation
 * @Description TODO
 * @Author DELL
 * @Date 2019/6/6 14:24
 * @Version 1.0
 */
public @interface MyAnnotation {
     int id();
     int synopsis();
     String engineer() default "unassigned";
     String date() default "unknown";
}
