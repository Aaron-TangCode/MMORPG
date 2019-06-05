package com.test.jdk8.lambda;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * @ClassName Demo01
 * @Description TODO
 * @Author DELL
 * @Date 2019/5/2910:19
 * @Version 1.0
 */
public class Demo01 {
    @Test
    public void test01(){
        String[] atp = {"Rafael Nadal", "Novak Djokovic",
                "Stanislas Wawrinka",
                "David Ferrer","Roger Federer",
                "Andy Murray","Tomas Berdych",
                "Juan Martin Del Potro"};

        List<String> strings = Arrays.asList(atp);
        
        //以前的遍历方式
        for (String list: strings) {
            System.out.println(list);
        }
        System.out.println("================================");
        //lambda表达方式
        strings.forEach((player1)-> System.out.println(player1));
        System.out.println("================================");
        //jdk8的双冒号操作符
        strings.forEach(System.out::println);
    }
}
