package com.game.server.request;

/**
 * @ClassName RequestBossInfoType
 * @Description Boss请求信息
 * @Author DELL
 * @Date 2019/8/9 15:19
 * @Version 1.0
 */
public class RequestBossInfoType {
    public static final int ENTERDUPLICATE = 0;
    public static final int CREATETEAM = 1;
    public static final int JOINTEAM = 2;
    public static final int EXITTEAM = 3;
    public static final int DISMISSTEAM = 4;
    public static final int QUERYTEAM = 5;
    public static final int TEAMATTACKBOSS = 6;
    public static final int USESKILLATTACKBOSS = 7;
}
