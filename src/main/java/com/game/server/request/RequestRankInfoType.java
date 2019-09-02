package com.game.server.request;

/**
 * @ClassName RequestRankInfoType
 * @Description 排行榜信息
 * @Author DELL
 * @Date 2019/8/9 15:19
 * @Version 1.0
 */
public class RequestRankInfoType {
    /**
     * 查询排行榜
     */
    public static final int QUERYRANK = 0;
    /**
     * 插入排行信息
     */
    public static final int INSERTRANK = 1;
    public static final int DELETERANK = 2;
    public static final int UPDATERANK = 3;
}
