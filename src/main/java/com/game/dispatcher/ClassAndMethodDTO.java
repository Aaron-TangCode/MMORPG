package com.game.dispatcher;

import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @ClassName ClassAndMethodDTO
 * @Description 这个类只是一个包装类
 * @Author DELL
 * @Date 2019/5/2815:20
 * @Version 1.0
 */
@SuppressWarnings("rawtypes")
@Component
public class ClassAndMethodDTO {
    private Class clz;
    private Method method;
    public Class getClz() {
        return clz;
    }
    public void setClz(Class clz) {
        this.clz = clz;
    }
    public Method getMethod() {
        return method;
    }
    public void setMethod(Method method) {
        this.method = method;
    }

}
