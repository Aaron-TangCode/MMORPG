package com.test.stackoverflow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @ClassName Question05
 * @Description 如何从数组创建ArrayList
 * @Author DELL
 * @Date 2019/6/10 14:42
 * @Version 1.0
 */
public class Question05 {
    public static void main(String[] args) {
        String[] strings = {new String("hello"),new String("world"),new String("Aaron")};

        for (int i = 0; i < strings.length; i++) {
            System.out.println(strings[i]);
        }

        List<String> list = new ArrayList<>(Arrays.asList(strings));
        for (int i = list.size() - 1; i >= 0; i--) {
            System.out.println(list.get(i));
        }
    }
}
