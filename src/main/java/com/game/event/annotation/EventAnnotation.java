package com.game.event.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 标记接口，标记事件
 */
@Component
@Retention(RetentionPolicy.RUNTIME)
public @interface EventAnnotation {

}
