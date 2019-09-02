package com.game.server.request;

/**
 * @ClassName RequestTaskInfoType
 * @Description 任务信息
 * @Author DELL
 * @Date 2019/8/9 15:19
 * @Version 1.0
 */
public class RequestTaskInfoType {
    /**
     * 查询未接受的任务
     */
    public static final int QUERYRECEIVABLETASK = 0;
    /**
     * 查询已接受的任务
     */
    public static final int QUERYRECEIVEDTASK = 1;
    /**
     * 接受任务
     */
    public static final int RECEIVETASK = 2;
    /**
     * 放弃任务
     */
    public static final int DISCARDTASK = 3;
}
