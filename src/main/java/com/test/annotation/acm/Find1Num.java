package com.test.annotation.acm;

/**
 * @author Aaron
 * @date 2019-09-12 17:48
 * @function
 */
public class Find1Num {
    public int NumberOf1Between1AndN_Solution(int n){
        //记录次数
        int count = 0;
        //遍历
        for(int i=1;i<=n;i++){
            //数字转字符串
            String target = String.valueOf(i);
            //字符串转字符
            char[] chars = target.toCharArray();

            for (int j=0;j<chars.length;j++){
                //比较
                if(chars[j]=='1'){
                    //++1
                    count++;
                }
            }


        }
        return count;
    }

    public static void main(String[] args) {
        Find1Num find1Num = new Find1Num();
        int num = find1Num.NumberOf1Between1AndN_Solution(13);
        System.out.println(num);
    }
}
