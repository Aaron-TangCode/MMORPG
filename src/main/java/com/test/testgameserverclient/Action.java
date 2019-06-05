package com.test.testgameserverclient;

import java.lang.reflect.Method;

/**
 * 为分发创建对象
 */
public class Action {
	
	private Method method;
	
	private Object object;
 
	public Method getMethod() {
		return method;
	}
 
	public void setMethod(Method method) {
		this.method = method;
	}
 
	public Object getObject() {
		return object;
	}
 
	public void setObject(Object object) {
		this.object = object;
	}
	
 
}