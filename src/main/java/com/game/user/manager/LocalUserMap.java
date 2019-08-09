package com.game.user.manager;

import com.game.role.bean.ConcreteRole;
import com.google.common.collect.Maps;
import io.netty.channel.Channel;

import java.util.Map;

/**
 * @ClassName LocalUserMap
 * @Description 全局userId和channelId
 * @Author DELL
 * @Date 2019/8/8 18:30
 * @Version 1.0
 */
public class LocalUserMap {
    private LocalUserMap(){}
    /**
     * key:userId
     * value:channel
     */
    private static volatile Map<Integer, Channel> userChannelMap = Maps.newConcurrentMap();
    /**
     * key:userId
     * value:ConcreteRole
     */
    private static volatile Map<Integer, ConcreteRole> userRoleMap = Maps.newConcurrentMap();

    /**
     * key:channel
     * value:userId
     */
    private static volatile Map<Channel, Integer> channelUserMap = Maps.newConcurrentMap();

    public static Map<Integer,Channel> getUserChannelMap(){
        return userChannelMap;
    }

    public static Map<Integer,ConcreteRole> getUserRoleMap(){
        return userRoleMap;
    }

    public static Map<Channel,Integer> getChannelUserMap(){
        return channelUserMap;
    }

}
