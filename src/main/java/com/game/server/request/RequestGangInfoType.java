package com.game.server.request;

/**
 * @ClassName RequestMapInfoType
 * @Description 工会信息
 * @Author DELL
 * @Date 2019/8/9 15:19
 * @Version 1.0
 */
public class RequestGangInfoType {
    /**
     * 创建工会
     */
    public static final int CREATEGANG = 0;
    /**
     * 加入工会
     */
    public static final int JOINGANG = 1;
    /**
     * 解散工会
     */
    public static final int DISMISSGANG = 2;
    /**
     * 捐赠
     */
    public static final int DONATEMONEY = 3;
}
