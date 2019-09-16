package com.test.annotation.acm;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Aaron
 * @date 2019-09-12 20:26
 * @function
 */
public class UglyNum {
    public int GetUglyNumber_Solution(int index) {
        if(index==0){
            return 0;
        }
        List<Integer> list = new ArrayList<>();
        //起始数
        list.add(1);
        //指针
        int num2 = 0;
        int num3 = 0;
        int num5 = 0;
        //添加丑数到List
        while(list.size()<index){
            int two = list.get(num2)*2;
            int three = list.get(num3)*3;
            int five = list.get(num5)*5;
            //找到更小数
            int smaller = Math.min(two,Math.min(three,five));

            if(smaller==two){
                num2++;
            }
            if(smaller==three){
                num3++;
            }
            if(smaller==five){
                num5++;
            }

            list.add(smaller);
        }

        return list.get(index-1);
    }

    public static void main(String[] args) {
        UglyNum uglyNum = new UglyNum();
        int i = uglyNum.GetUglyNumber_Solution(2);
        System.out.println(i);
    }
}
