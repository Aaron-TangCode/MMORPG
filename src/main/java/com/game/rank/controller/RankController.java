package com.game.rank.controller;

import com.game.annotation.ExcelAnnotation;
import com.game.dispatcher.RequestAnnotation;
import com.game.rank.bean.RankBean;
import com.game.rank.manager.RankManager;
import com.game.rank.service.RankService;
import com.game.role.bean.ConcreteRole;
import com.game.utils.MapUtils;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * @ClassName RankController
 * @Description 排名数组：位置s是战斗力，rank[s]是排名
 * @Author DELL
 * @Date 2019/8/5 16:20
 * @Version 1.0
 */
@ExcelAnnotation
@RequestAnnotation("/rank")
@Controller
public class RankController {

    @Autowired
    private RankService rankService;
    /**
     * 查看排名
     */
    @RequestAnnotation("/queryRank")
    public void queryRank(String roleName){
        queryHandler(roleName);
    }

    private void queryHandler(String roleName) {
        ConcreteRole role = getRole(roleName);
        //获取数据
        RankBean[] rankBeans = RankManager.getRankBeans();
        int[] ranks = RankManager.getRanks();
        //输出
        Channel channel = role.getCtx().channel();
        channel.writeAndFlush("【排名】\t\t"+"[角色]\t\t"+"[战斗力]\n");
        //从积分高，到积分低排名
        for (int i = ranks.length - 1; i >= 0; i--) {
            if(rankBeans[i]!=null){
                int comat = rankBeans[i].getComat();
                ConcreteRole tmpRole = rankBeans[i].getRole();
                channel.writeAndFlush(ranks[comat]+"\t\t"+tmpRole.getName()+"\t\t"+comat+"\n");
            }
        }
    }

    public ConcreteRole getRole(String roleName){
        return MapUtils.getMapRolename_Role().get(roleName);
    }
    @RequestAnnotation("/insertRank")
    public void insertRank(){

    }
    @RequestAnnotation("/updateRank")
    public void updateRank(){

    }
    @RequestAnnotation("/deleteRank")
    public void deleteRank(){

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
