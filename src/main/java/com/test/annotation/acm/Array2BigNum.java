package com.test.annotation.acm;

import java.util.Arrays;

/**
 * @author Aaron
 * @date 2019-09-12 18:38
 * @function
 */
public class Array2BigNum {
    public String PrintMinNumber(int [] numbers) {
        //有效性校验
        if(numbers==null||numbers.length==0){
            return null;
        }
        //整形数组转字符串数组
        int n = numbers.length;
        String[] nums = new String[n];

        for(int i=0;i<n;i++){
            nums[i] = String.valueOf(numbers[i]);
        }
        //字符串比较
        Arrays.sort(nums,(s1,s2)->{
            return (s1+s2).compareTo((s2+s1));
        });
        StringBuffer sb = new StringBuffer();
        //构建最小整数
        for (String num : nums) {
            sb.append(num);
        }
        //返回
        return sb.toString();
    }

    public static void main(String[] args) {
        Array2BigNum array2BigNum = new Array2BigNum();
        int[] numbers = new int[]{3,32,321};
        String s = array2BigNum.PrintMinNumber(numbers);
        System.out.println(s);
    }
}
