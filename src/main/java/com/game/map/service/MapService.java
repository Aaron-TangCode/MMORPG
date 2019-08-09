package com.game.map.service;

import com.game.map.bean.ConcreteMap;
import com.game.map.repository.MapRepository;
import com.game.protobuf.message.ResultCode;
import com.game.protobuf.protoc.MsgMapInfoProto;
import com.game.role.bean.ConcreteRole;
import com.game.user.manager.LocalUserMap;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName MapService
 * @Description 地图service
 * @Author DELL
 * @Date 2019/5/2921:05
 * @Version 1.0
 */
@Service("MapService")
public class MapService {
    @Autowired
    private MapRepository mapRepository;

    /**
     * 根据id获取地图实体类
     * @param id
     * @return
     */
    public ConcreteMap getMap(int id){
        return mapRepository.getMap(id);
    }

    /**
     * 根据地图名字获取地图id
     * @param name
     * @return
     */
    public int getMapIdByMapName(String name) {
        return mapRepository.getId(name);
    }

    public MsgMapInfoProto.ResponseMapInfo getMapInfo(Channel channel, MsgMapInfoProto.RequestMapInfo requestMapInfo) {
        //获取userId
        Integer userId = LocalUserMap.getChannelUserMap().get(channel);
        ConcreteRole role = LocalUserMap.getUserRoleMap().get(userId);
        //获取地图
        ConcreteMap concreteMap = mapRepository.getMapByRoleId(role.getId());
        String content = "【玩家:】"+role.getName()+"在地图:"+concreteMap.getName();

        return MsgMapInfoProto.ResponseMapInfo.newBuilder()
                .setType(MsgMapInfoProto.RequestType.GETMAP)
                .setResult(ResultCode.SUCCESS)
                .setContent(content)
                .build();
    }

    public MsgMapInfoProto.ResponseMapInfo move(Channel channel, MsgMapInfoProto.RequestMapInfo requestMapInfo) {
        return null;
    }
}
