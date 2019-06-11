package com.test.stackoverflow;

/**
 * @ClassName Question03
 * @Description i+=j问题
 * @Author DELL
 * @Date 2019/6/10 14:29
 * @Version 1.0
 */
public class Question03 {
    public static void main(String[] args) {
        int i = 3;
        int k = 3;
        double j = 4.3;
       // i = i + j;//无法编译
        i +=j;//可以编译，实际上i+=j,是等价于i = (int)(i+j)
        j +=k;//j = (double)(j+k)
        System.out.println("i:"+i);
        System.out.println("j:"+j);
    }
}
