package com.game.server.request;

/**
 * @ClassName RequestMapInfoType
 * @Description 拍卖行请求信息类型
 * @Author DELL
 * @Date 2019/8/9 15:19
 * @Version 1.0
 */
public class RequestAuctionInfoType {
    /**
     * 拍卖
     */
    public static final int BIDDING = 0;
    /**
     * 下架
     */
    public static final int RECYCLE = 1;
    /**
     * 上架
     */
    public static final int PUBLISH = 2;
    /**
     * 查询拍卖行的拍卖品
     */
    public static final int QUERYAUCTION = 3;
}
