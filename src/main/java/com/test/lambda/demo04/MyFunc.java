package com.test.lambda.demo04;

/**
 * @author Aaron
 * @date 2019-09-12 11:47
 * @function
 */
@FunctionalInterface
public interface MyFunc<T,R> {
    public R getValue(T t1,T t2);
}
