package com.game.rank.service;

import com.game.protobuf.protoc.MsgRankInfoProto;
import com.game.rank.bean.RankBean;
import com.game.rank.manager.RankManager;
import com.game.rank.repository.RankRepository;
import com.game.role.bean.ConcreteRole;
import com.game.user.manager.LocalUserMap;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName RankService
 * @Description 排行榜服务
 * @Author DELL
 * @Date 2019/8/5 16:20
 * @Version 1.0
 */
@Service
public class RankService {
    /**
     * 排行榜数据访问
     */
    @Autowired
    private RankRepository rankRepository;

    /**
     * 查询排行榜
     * @return list
     */
    public List<RankBean> queryRank() {
        return rankRepository.queryRank();
    }

    /**
     * 查询排行榜
     * @param channel channel
     * @param requestRankInfo 排行榜请求信息
     * @return 协议信息
     */
    public MsgRankInfoProto.ResponseRankInfo queryRankInfo(Channel channel, MsgRankInfoProto.RequestRankInfo requestRankInfo) {
        //获取数据
        RankBean[] rankBeans = RankManager.getRankBeans();
        int[] ranks = RankManager.getRanks();
        StringBuffer content = new StringBuffer();
        content.append("【排名】\t\t"+"[角色]\t\t"+"[战斗力]\n");
        //从积分高，到积分低排名
        for (int i = ranks.length - 1; i >= 0; i--) {
            if(rankBeans[i]!=null){
                //获取战力
                int comat = rankBeans[i].getComat();
                //获取角色
                ConcreteRole tmpRole = rankBeans[i].getRole();
                //content
                content.append(ranks[comat]+"\t\t"+tmpRole.getName()+"\t\t"+comat+"\n");
            }
        }
        //返回协议信息
        return MsgRankInfoProto.ResponseRankInfo.newBuilder()
                .setContent(content.toString())
                .setType(MsgRankInfoProto.RequestType.QUERYRANK)
                .build();

    }

    /**
     * 插入信息
     * @param channel channel
     * @param requestRankInfo 排行榜请求信息
     * @return 协议信息
     */
    public MsgRankInfoProto.ResponseRankInfo insertRankInfo(Channel channel, MsgRankInfoProto.RequestRankInfo requestRankInfo) {
        //获取role
        ConcreteRole role = getRole(channel);
        //添加到本地缓存
        int[] ranks = RankManager.getRanks();
        RankBean[] rankBeans = RankManager.getRankBeans();
        //插入role
//        rankBeans[role.getAttack()] = role;
        //根据战力（攻击力），插入排行榜
        Integer attack = role.getAttack();
        //获取当前排名
        int index = RankManager.getIndex();
        //判断attack位置有没有元素,0:没元素；非0：有元素，向滑动一位
        if(ranks[attack]==0){
            ranks[attack] = index++;
        }else{
            if(attack-1>=0){
                ranks[attack-1] = index++;
            }
        }
        //新建
        return MsgRankInfoProto.ResponseRankInfo.newBuilder()
                .setType(MsgRankInfoProto.RequestType.INSERTRANK)
                .setContent("成功插入排行榜")
                .build();
    }

    /**
     *
     * @param channel channel
     * @param requestRankInfo 排行榜请求信息
     * @return 协议信息
     */
    public MsgRankInfoProto.ResponseRankInfo updateRankInfo(Channel channel, MsgRankInfoProto.RequestRankInfo requestRankInfo) {
        return null;
    }

    /**
     *
     * @param channel channel
     * @param requestRankInfo 排行榜请求信息
     * @return 协议信息
     */
    public MsgRankInfoProto.ResponseRankInfo deleteRankInfo(Channel channel, MsgRankInfoProto.RequestRankInfo requestRankInfo) {
        return null;
    }
    public ConcreteRole getRole(Channel channel){
        Integer userId = LocalUserMap.getChannelUserMap().get(channel);

        ConcreteRole role = LocalUserMap.getUserRoleMap().get(userId);
        return role;
    }
}
