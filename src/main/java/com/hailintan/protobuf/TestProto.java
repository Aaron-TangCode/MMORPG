package com.hailintan.protobuf;

import com.google.protobuf.InvalidProtocolBufferException;

/**
 * @ClassName TestProto
 * @Description TODO
 * @Author DELL
 * @Date 2019/5/2321:21
 * @Version 1.0
 */
public class TestProto {
    public static void main(String[] args) throws InvalidProtocolBufferException {
        //初始化字段
        DataInfo.Student student = DataInfo.Student.newBuilder()
                .setAge(23).setAddress("广州").setName("Aaron").build();
        //把对象序列化（转为字节数组）
        byte[] bytes = student.toByteArray();

        //可以在网络传输，从A机器传输字节数组给B机器

        //把对象进行反序列化（转为对象）
        DataInfo.Student student1 = DataInfo.Student.parseFrom(bytes);

        System.out.println(student1.getAge());
        System.out.println(student1.getName());
        System.out.println(student1.getAddress());


    }
}
