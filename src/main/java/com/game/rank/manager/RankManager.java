package com.game.rank.manager;


import com.game.rank.bean.RankBean;

/**
 * @ClassName RankManager
 * @Description 排名数组：位置s是战斗力，rank[s]是排名
 * @Author DELL
 * @Date 2019/8/5 17:15
 * @Version 1.0
 */
public class RankManager {
    //排名数组：位置s是战斗力，rank[s]是排名
    private static RankBean[] rankBeans = new RankBean[10000];
    private static int[] ranks = new int[10000];

    private RankManager(){}

    public static RankBean[] getRankBeans(){
        return rankBeans;
    }
    public static int[] getRanks(){
        return ranks;
    }
}
