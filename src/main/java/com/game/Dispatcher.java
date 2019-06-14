package com.game;


import java.util.Map;

/**
 * @ClassName Dispatcher
 * @Description TODO
 * @Author DELL
 * @Date 2019/6/14 16:07
 * @Version 1.0
 */
public class Dispatcher {
    public static Map<String,Handeler> map;



    public static void main(String[] args) {
        map.get(args[0]).execute();
    }



}
