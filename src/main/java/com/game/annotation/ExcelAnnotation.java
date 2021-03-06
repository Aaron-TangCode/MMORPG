package com.game.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author Aaron
 * @date 2019/6/8 5:59 PM
 * @function 识别excel静态数据,纯标记
 */
@Component
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelAnnotation {
}
