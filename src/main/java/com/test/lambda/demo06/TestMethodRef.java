package com.test.lambda.demo06;

import org.junit.Test;

import java.io.PrintStream;
import java.util.function.BiPredicate;
import java.util.function.Consumer;

/**
 * @author Aaron
 * @date 2019-09-16 10:34
 * @function
 */
public class TestMethodRef {
    @Test
    public void test01(){
        PrintStream printStream = System.out;
        //先实现函数
        Consumer<String> consumer = printStream::println;
        //后调用
        consumer.accept("567890");
    }

    @Test
    public void test02(){
        BiPredicate<String,String> predicate = (x,y)->x.equals(y);

        BiPredicate<String,String> biPredicate = String::equals;

        boolean test = biPredicate.test("123", "123");
        System.out.println(test);
    }
}
