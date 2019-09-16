package com.test.lambda.demo04;

import org.junit.Test;

/**
 * @author Aaron
 * @date 2019-09-12 12:06
 * @function
 */
public class TestLambda {
    @Test
    public void test01(){
        long l = get((x, y) -> x + y, 100, 99);
        System.out.println(l);

        long l1 = get((x, y) -> x * y, 100, 99);
        System.out.println(l1);
    }

    public long get(MyFunc<Long,Long> myFunc,long t1,long t2){
        return myFunc.getValue(t1,t2);
    }
}
