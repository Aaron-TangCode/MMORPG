package com.game.utils;

/**
 * @ClassName EnumUtil
 * @Description 枚举工具类
 * @Author DELL
 * @Date 2019/7/29 21:29
 * @Version 1.0
 */
public class EnumUtil {

    public  static QuestType string2Enum(String s) {
       for (QuestType e : QuestType.values()) {
           if (e.toString().equals(s)) {
               return e;
           }
       }
       return null;
    }




    public static void main(String[] args) {

        System.out.println(string2Enum("aaa"));

    }


}
