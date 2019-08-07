package com.hailintang.protobuf;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @ClassName Test
 * @Description TODO
 * @Author DELL
 * @Date 2019/8/7 12:03
 * @Version 1.0
 */
public class Test {
    public static void main(String[] args) throws IOException {
        //按照创建的数据结构，创建一个对象
        UserInfo.UserMsg.Builder userInfo = UserInfo.UserMsg.newBuilder();
        userInfo.setId(1);
        userInfo.setAge(22);
        userInfo.setName("tanghailin");
        UserInfo.UserMsg build = userInfo.build();
        //将数据写到输出流
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        build.writeTo(outputStream);
        //将数据序列化后输出
        byte[] bytes = outputStream.toByteArray();
        //接收并读取
        ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
        //反序列化
        UserInfo.UserMsg userInfo2 = UserInfo.UserMsg.parseFrom(bytes);
        //打印
        System.out.println(userInfo2.getId());
        System.out.println(userInfo2.getName());
        System.out.println(userInfo2.getAge());

    }
}
