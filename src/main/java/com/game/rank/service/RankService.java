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
 * @Description TODO
 * @Author DELL
 * @Date 2019/8/5 16:20
 * @Version 1.0
 */
@Service
public class RankService {
    @Autowired
    private RankRepository rankRepository;

    public List<RankBean> queryRank() {
        return rankRepository.queryRank();
    }

    public MsgRankInfoProto.ResponseRankInfo queryRankInfo(Channel channel, MsgRankInfoProto.RequestRankInfo requestRankInfo) {
        ConcreteRole role = getRole(channel);
        //获取数据
        RankBean[] rankBeans = RankManager.getRankBeans();
        int[] ranks = RankManager.getRanks();
        StringBuffer content = new StringBuffer();
        content.append("【排名】\t\t"+"[角色]\t\t"+"[战斗力]\n");
        //从积分高，到积分低排名
        for (int i = ranks.length - 1; i >= 0; i--) {
            if(rankBeans[i]!=null){
                int comat = rankBeans[i].getComat();
                ConcreteRole tmpRole = rankBeans[i].getRole();
                content.append(ranks[comat]+"\t\t"+tmpRole.getName()+"\t\t"+comat+"\n");
            }
        }
        return MsgRankInfoProto.ResponseRankInfo.newBuilder()
                .setContent(content.toString())
                .setType(MsgRankInfoProto.RequestType.QUERYRANK)
                .build();

    }

    public MsgRankInfoProto.ResponseRankInfo insertRankInfo(Channel channel, MsgRankInfoProto.RequestRankInfo requestRankInfo) {
        return null;
    }

    public MsgRankInfoProto.ResponseRankInfo updateRankInfo(Channel channel, MsgRankInfoProto.RequestRankInfo requestRankInfo) {
        return null;
    }

    public MsgRankInfoProto.ResponseRankInfo deleteRankInfo(Channel channel, MsgRankInfoProto.RequestRankInfo requestRankInfo) {
        return null;
    }
    public ConcreteRole getRole(Channel channel){
        Integer userId = LocalUserMap.getChannelUserMap().get(channel);

        ConcreteRole role = LocalUserMap.getUserRoleMap().get(userId);
        return role;
    }
}
