package com.game.server.request;

/**
 * @ClassName RequestTradeInfoType
 * @Description 交易信息
 * @Author DELL
 * @Date 2019/8/9 15:19
 * @Version 1.0
 */
public class RequestTradeInfoType {
    /**
     * 请求交易
     */
    public static final int REQUESTTRADE = 0;
    /**
     * 确认交易
     */
    public static final int CONFIRMTRADE = 1;
    /**
     * 交易物品
     */
    public static final int TRADINGGOODS = 2;
    /**
     * 交易金币
     */
    public static final int TRADINGMONEY = 3;
    /**
     * 交易
     */
    public static final int TRADEGOODS = 4;

}
