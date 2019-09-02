package com.game.server.request;

/**
 * @ClassName RequestBossInfoType
 * @Description Boss请求信息
 * @Author DELL
 * @Date 2019/8/9 15:19
 * @Version 1.0
 */
public class RequestBossInfoType {
    /**
     * 进入副本
     */
    public static final int ENTERDUPLICATE = 0;
    /**
     * 创建队伍
     */
    public static final int CREATETEAM = 1;
    /**
     * 加入队伍
     */
    public static final int JOINTEAM = 2;
    /**
     * 退出队伍
     */
    public static final int EXITTEAM = 3;
    /**
     * 解散队伍
     */
    public static final int DISMISSTEAM = 4;
    /**
     * 查询队伍
     */
    public static final int QUERYTEAM = 5;
    /**
     * 团队打副本
     */
    public static final int TEAMATTACKBOSS = 6;
    /**
     * 使用技能攻击怪兽
     */
    public static final int USESKILLATTACKBOSS = 7;
}
