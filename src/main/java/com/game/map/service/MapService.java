package com.game.map.service;

import com.game.buff.handler.BuffHandler;
import com.game.map.bean.ConcreteMap;
import com.game.map.repository.MapRepository;
import com.game.protobuf.message.ResultCode;
import com.game.protobuf.protoc.MsgMapInfoProto;
import com.game.role.bean.ConcreteRole;
import com.game.role.service.RoleService;
import com.game.user.manager.LocalUserMap;
import com.game.utils.MapUtils;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.game.buff.handler.BuffType.RED;

/**
 * @ClassName MapService
 * @Description 地图service
 * @Author DELL
 * @Date 2019/5/2921:05
 * @Version 1.0
 */
@Service("MapService")
public class MapService {
    /**
     * 地图的数据访问
     */
    @Autowired
    private MapRepository mapRepository;
    /**
     * 角色服务
     */
    @Autowired
    private RoleService roleService;
    /**
     * buff控制器
     */
    @Autowired
    private BuffHandler buffHandler;
    /**
     * 根据id获取地图实体类
     * @param id id
     * @return map
     */
    public ConcreteMap getMap(int id){
        return mapRepository.getMap(id);
    }

    /**
     * 根据地图名字获取地图id
     * @param name name
     * @return the id of map
     */
    public int getMapIdByMapName(String name) {
        return mapRepository.getId(name);
    }

    /**
     * 获取地图信息
     * @param channel channel
     * @param requestMapInfo 地图请求信息
     * @return 协议信息
     */
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

    /**
     * 移动
     * @param channel channel
     * @param requestMapInfo 地图请求信息
     * @return 协议信息
     */
    public MsgMapInfoProto.ResponseMapInfo move(Channel channel, MsgMapInfoProto.RequestMapInfo requestMapInfo) {
        //获取userId
        Integer userId = LocalUserMap.getChannelUserMap().get(channel);
        //获取role
        ConcreteRole role = LocalUserMap.getUserRoleMap().get(userId);
        //获取地图
        ConcreteMap concreteMap = mapRepository.getMapByRoleId(role.getId());
        //获取目的地
        String dest = requestMapInfo.getDest();
        //获取角色的原地点
        String src = role.getConcreteMap().getName();
        //获取源地点和目的地点的id
        int src_id = getMapIdByMapName(src);
        int dest_id = getMapIdByMapName(dest);
        //判断是否可达
        boolean isAccess = MapUtils.isReach(src_id,dest_id);
        String content = null;
        if(isAccess){
            //激活buff
            startBuff(role,dest);
            //从src移动到dest,更新数据库
            roleService.updateMap(role.getName(),dest_id);
            role.getConcreteMap().setName(dest);
            role.getConcreteMap().setId(dest_id);
            //更新本地缓存
            MapUtils.getMapRolename_Role().put(role.getName(),role);
             content =  role.getName()+"从"+src+"移动到"+dest;
        }else{
             content =  "不能从"+src+"直接移动到"+dest;
        }
        return MsgMapInfoProto.ResponseMapInfo.newBuilder()
                .setType(MsgMapInfoProto.RequestType.MOVE)
                .setContent(content)
                .build();
    }

    /**
     * 激活buff
     * @param role 角色
     * @param dest 目的地
     */
    private void startBuff(ConcreteRole role,String dest) {
        if(!dest.equals("村子")){
            buffHandler.executeBuff(role.getName(),RED);
        }
    }
    /**
     * 切换地图
     * @param roleName 角色名
     * @param dest 目的地
     * @return 消息
     */
    public String moveTo(String roleName,String dest) {
        //获取角色信息
        ConcreteRole role = MapUtils.getMapRolename_Role().get(roleName);
        //获取角色的原地点
        String src = role.getConcreteMap().getName();
        //获取源地点和目的地点的id
        int src_id = getMapIdByMapName(src);
        int dest_id = getMapIdByMapName(dest);
        //判断是否可达
        boolean isAccess = MapUtils.isReach(src_id,dest_id);
        if(isAccess){
            //从src移动到dest,更新数据库
            roleService.updateMap(role.getName(),dest_id);
            role.getConcreteMap().setName(dest);
            role.getConcreteMap().setId(dest_id);
            //更新本地缓存
            MapUtils.getMapRolename_Role().put(roleName,role);
            return role.getName()+"从"+src+"移动到"+dest;
        }else{
            return "不能从"+src+"直接移动到"+dest;
        }
    }
}
