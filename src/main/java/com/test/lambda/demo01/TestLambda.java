package com.test.lambda.demo01;

import org.junit.Test;

import java.util.Comparator;
import java.util.function.Consumer;

/**
 * @author Aaron
 * @date 2019-09-11 18:14
 * @function
 */
public class TestLambda {
    /**
     * 无参数，无返回值
     */
    @Test
    public void test01(){
        int num = 0;
        Runnable r1 = new Runnable() {
            @Override
            public void run() {
                System.out.println("hello runnable"+num);
            }
        };
        r1.run();
        Runnable r2 = ()-> System.out.println("hello lambda"+num);
        r2.run();
    }

    /**
     * 有参数，无返回值
     */
    @Test
    public void test02(){
        Consumer<String> consumer = (a)-> System.out.println(a);

        consumer.accept("asb");
    }

    /**
     * 有两个以上参数，有返回值，并且语句有两条以上，要写大括号
     */
    @Test
    public void test03(){
        Comparator<Integer> com = (x,y)->{
            System.out.println("x和y谁打谁小");
            return Integer.compare(x,y);
        };
    }

    /**
     * 有两个以上参数，有返回值，并且语句只有一条，可以省略大括号和return
     */
    @Test
    public void test04(){
        Comparator<Integer> comparator = (a,b)->Integer.compare(a,b);
    }

    @Test
    public void test05(){
        System.out.println(operation(100,(x)->{return x+x;}));
    }

    public Integer operation(Integer num,Myfunc myfunc){
        return myfunc.getVal(num);
    }
}
