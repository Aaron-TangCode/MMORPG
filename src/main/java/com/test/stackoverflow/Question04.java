package com.test.stackoverflow;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * @ClassName Question04
 * @Description 遍历HashMap的最佳方法---------->entrySet()
 * @Author DELL
 * @Date 2019/6/10 14:38
 * @Version 1.0
 */
public class Question04 {
    public static void main(String[] args) {
        Map<String,String> map = new HashMap<>();
        map.put("1","小明");
        map.put("2","小龙");
        map.put("3","小马哥");

        Set<Map.Entry<String, String>> entrySet = map.entrySet();
        Iterator<Map.Entry<String, String>> iterator = entrySet.iterator();
        while(iterator.hasNext()){
            Map.Entry<String, String> next = iterator.next();
            System.out.println(next.getKey()+"======"+next.getValue());
        }
    }
}
