package com.game.dispatcher; /**
@Target 不指定Target代表可以在任何地方可以添加此注解  但是使用时只在类上和方法上使用   模拟Springmvc的RequeMapping
*/

import org.springframework.stereotype.Component;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
/**
 * 自定义注解类  用来标记控制器类
 * @Target 不指定Target代表可以在任何地方可以添加此注解  但是使用时只在类上和方法上使用   模拟Springmvc的RequeMapping
 */
@Component
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestAnnotation {
	/**
	 * 资源名
	 * @return
	 */
	String value();//只有一个字段  用来接收用户请求的资源  资源类似   /user/login 格式
}