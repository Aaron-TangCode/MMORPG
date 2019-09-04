package com.game.map.service;

import com.game.buff.handler.BuffHandler;
import com.game.event.beanevent.AttackedEvent;
import com.game.event.beanevent.GoVillageEvent;
import com.game.event.manager.EventMap;
import com.game.map.bean.ConcreteMap;
import com.game.map.repository.MapRepository;
import com.game.protobuf.message.ResultCode;
import com.game.protobuf.protoc.MsgMapInfoProto;
import com.game.role.bean.ConcreteRole;
import com.game.role.service.RoleService;
import com.game.user.manager.LocalUserMap;
import com.game.utils.CacheUtils;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.game.buff.handler.BuffType.BLUE;
import static com.game.buff.handler.BuffType.RED;

/**
 * @ClassName MapService
 * @Description 地图service
 * @Author DELL
 * @Date 2019/5/2921:05
 * @Version 1.0
 */
@Service
public class MapService {
    /**
     * 回村子事件
     */
    @Autowired
    private GoVillageEvent goVillageEvent;
    @Autowired
    private AttackedEvent attackedEvent;
    /**
     * 事件map
     */
    @Autowired
    private EventMap eventMap;
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
        String src = concreteMap.getName();
        //获取源地点和目的地点的id
        int srcId = getMapIdByMapName(src);
        int destId = getMapIdByMapName(dest);
        //判断是否可达
        boolean isAccess = CacheUtils.isReach(srcId,destId);
        String content = null;
        if(isAccess){
            //激活buff
            buffHandle(role,dest);
            //添加role到map
            addRole2Map(role,destId);
            //从map移除role
            rmRoleFromMap(role,srcId);
            //从src移动到dest,更新数据库
            roleService.updateMap(role.getName(),destId);
            role.getConcreteMap().setName(dest);
            role.getConcreteMap().setId(destId);
            //更新本地缓存
            CacheUtils.getMapRoleNameRole().put(role.getName(),role);
             content =  role.getName()+"从"+src+"移动到"+dest;
        }else{
             content =  "不能从"+src+"直接移动到"+dest;
        }
        return MsgMapInfoProto.ResponseMapInfo.newBuilder()
                .setType(MsgMapInfoProto.RequestType.MOVE)
                .setContent(content)
                .build();
    }

    private void rmRoleFromMap(ConcreteRole role, int srcId) {
        //获取地图
        ConcreteMap map = CacheUtils.getMapMap().get(srcId);
        //移除role
        map.getRoleList().remove(role);
    }

    /**
     * 添加role到map
     * @param role
     * @param destId
     */
    private void addRole2Map(ConcreteRole role,int destId) {
        //获取地图
        ConcreteMap map = CacheUtils.getMapMap().get(destId);
        //添加role
        map.getRoleList().add(role);
    }

    /**
     * 处理buff
     * @param role 角色
     * @param dest 目的地
     */
    private void buffHandle(ConcreteRole role,String dest){
        startBuff(role,dest);
        stopBuff(role,dest);
    }
    /**
     * 停止buff
     * @param role 角色
     * @param dest 目的地
     */
    private void stopBuff(ConcreteRole role, String dest) {
        if(dest.equals("村子")){
            //触发事件
            goVillageEvent.setRole(role);
            //add
            eventMap.submit(goVillageEvent);
            //如果role死亡
            if(role.getCurHp()<=0){
                recovery(role);
            }
        }else if (dest.equals("起始之地")){
            //取消红蓝buff
            attackedEvent.setRole(role);
            eventMap.submit(attackedEvent);
            if(role!=null&role.getQueue()!=null){
                role.getQueue().clear();
            }
        }
    }
    private void recovery(ConcreteRole role){
        //回血
        role.setCurHp(role.getTotalHp()/2);
        //回蓝
        role.setCurMp(role.getTotalMp()/2);
    }
    /**
     * 激活buff
     * @param role 角色
     * @param dest 目的地
     */
    private void startBuff(ConcreteRole role, String dest) {
        if(dest.equals("村子")){
            //激活Buff
            buffHandler.executeBuff(role.getName(),RED);
            try {
                Thread.sleep(2000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            buffHandler.executeBuff(role.getName(),BLUE);
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
        ConcreteRole role = CacheUtils.getMapRoleNameRole().get(roleName);
        //获取角色的原地点
        String src = role.getConcreteMap().getName();
        //获取源地点和目的地点的id
        int srcId = getMapIdByMapName(src);
        int destId = getMapIdByMapName(dest);
        //判断是否可达
        boolean isAccess = CacheUtils.isReach(srcId,destId);
        if(isAccess){
            //从src移动到dest,更新数据库
            roleService.updateMap(role.getName(),destId);
            role.getConcreteMap().setName(dest);
            role.getConcreteMap().setId(destId);
            //添加role到map
            addRole2Map(role,destId);
            //从map移除role
            rmRoleFromMap(role,srcId);
            //更新本地缓存
            CacheUtils.getMapRoleNameRole().put(roleName,role);
            return role.getName()+"从"+src+"移动到"+dest;
        }else{
            return "不能从"+src+"直接移动到"+dest;
        }
    }

}
