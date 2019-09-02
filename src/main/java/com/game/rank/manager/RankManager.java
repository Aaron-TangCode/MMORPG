package com.game.rank.manager;


import com.game.annotation.ExcelAnnotation;
import com.game.rank.bean.RankBean;
import com.game.rank.service.RankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @ClassName RankManager
 * @Description 排名数组：位置s是战斗力，rank[s]是排名
 * @Author DELL
 * @Date 2019/8/5 17:15
 * @Version 1.0
 */
@Component
@ExcelAnnotation
public class RankManager {
    /**
     * 排行榜service
     */
    @Autowired
    private RankService rankService;
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
    @ExcelAnnotation
    public void initData(){
        //查询数据
        List<RankBean> rankBeanList = rankService.queryRank();
        //装载数据
        RankBean[] rankBeans = RankManager.getRankBeans();
        int[] ranks = RankManager.getRanks();
        for (int i = 0; i < rankBeanList.size(); i++) {
            RankBean rankBean = rankBeanList.get(i);
            rankBeans[i] = rankBean;
            //做一个标记
            ranks[rankBean.getComat()] = -1;
        }
        //排名
        for (int i = ranks.length - 1,j = 1; i >= 0; i--) {
            if(ranks[i]==-1){
                ranks[i] = j++;
            }
        }
    }
}
