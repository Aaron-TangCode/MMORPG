package com.test.lambda.demo05;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * @author Aaron
 * @date 2019-09-16 09:44
 * @function
 */
public class TestLambda {
    /**
     * 测试Consumer函数式接口
     */
    @Test
    public void test01(){
        happy(300,(x)->{
            System.out.println("吃饭花了"+x+"元");
        });
    }

    public void happy(int num, Consumer<Integer> consumer){
        consumer.accept(num);
    }

    @Test
    public void test02(){
        //产生10个随机数，且是偶数的数放进集合
        List<Integer> list = getList(10, () -> {
            int num = (int) (Math.random() * 100);
            if (num % 2 == 0) {
                return num;
            } else {
                return -1;
            }
        });

        //打印列表
        for (Integer integer : list) {
            System.out.print(integer+" ");
        }
    }

    /**
     * 需求：产生指定个数的集合
     * @return
     */
    public List<Integer> getList(int num, Supplier<Integer> supplier){
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            Integer number = supplier.get();
            list.add(number);
        }
        return list;
    }

    /**
     * 需求：处理字符串
     */
    @Test
    public void test03(){
        String str = getStr("\t\t\t 加急到我酒叟你觉得   ", (x) -> {
            return x.trim();
        });

        System.out.println(str);

        String str1 = getStr("加急到我酒叟你觉得", (x) -> {
            return x.substring(2, 6);
        });

        System.out.println(str1);
    }

    public String getStr(String str, Function<String,String> function){
        return function.apply(str);
    }

    @Test
    public void test04(){


        List<String> list = Arrays.asList("qweqwe", "hbdsfsjkh", "qw", "f");

        List<String> strings = filterList(list, (x) -> {
            return x.length() <=2;
        });

        for (String string : strings) {
            System.out.println(string);
        }
    }

    public List<String> filterList(List<String> list, Predicate<String> predicate){
        ArrayList<String> strlist = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            if (predicate.test(list.get(i))) {
                strlist.add(list.get(i));
            }
        }
        return strlist;
    }
}
