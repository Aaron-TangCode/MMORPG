package com.test.stackoverflow;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @ClassName Question01
 * @Description 时区问题（环境：jdk1.6）
 * @Author DELL
 * @Date 2019/6/10 12:14
 * @Version 1.0
 */
public class Question01 {
    public static void main(String[] args) throws ParseException {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str3 = "1927-12-31 23:54:07";
        String str4 = "1927-12-31 23:54:08";
        Date sDt3 = sf.parse(str3);
        Date sDt4 = sf.parse(str4);
        long ld3 = sDt3.getTime() /1000;
        long ld4 = sDt4.getTime() /1000;
        System.out.println(ld4-ld3);
    }
}
