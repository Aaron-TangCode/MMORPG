package com.test.lambda.demo03;

import org.junit.Test;

/**
 * @author Aaron
 * @date 2019-09-12 11:26
 * @function
 */
public class TestLambda {
    @Test
    public void test01(){
        String str = operation((x) -> {
            return x.toUpperCase();
        }, "abCwerty");

        System.out.println(str);

        String s = operation((x) -> {
            return x.substring(2, 4);
        }, "qweqqwe");

        System.out.println(s);

    }

    public String operation(MyFunc myFunc,String lowerStr){
        return myFunc.getValue(lowerStr);
    }
}
