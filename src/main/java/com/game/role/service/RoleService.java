package com.game.role.service;

import com.alibaba.fastjson.JSONObject;
import com.game.backpack.bean.Goods;
import com.game.backpack.service.BackpackService;
import com.game.property.bean.PropertyType;
import com.game.protobuf.message.ContentType;
import com.game.protobuf.message.ResultCode;
import com.game.protobuf.protoc.MsgRoleInfoProto;
import com.game.protobuf.service.ProtoService;
import com.game.role.bean.ConcreteRole;
import com.game.role.controller.RegisterRole;
import com.game.role.manager.InjectRoleProperty;
import com.game.role.repository.RoleRepository;
import com.game.user.bean.User;
import com.game.user.manager.LocalUserMap;
import com.game.user.repository.UserRepository;
import com.game.utils.MapUtils;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @ClassName RoleService
 * @Description 角色service
 * @Author DELL
 * @Date 2019/5/2920:47
 * @Version 1.0
 */
@Service("RoleService")
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private RegisterRole registerRole;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BackpackService backpackService;

    @Autowired
    private ProtoService protoService;
    /**
     * 获取角色role
     * @param id
     * @return
     */
    public ConcreteRole getRole(int id){
        return roleRepository.getRole(id);
    }

    /**
     * 根据角色名roleName获取地图map
     * @param roleName
     * @return
     */
    public String getMapByRoleName(String roleName) {
        return roleRepository.getMapByRoleName(roleName);
    }

    /**
     * 更新角色所在的地图
     * @param roleName
     * @param dest
     * @return
     */
    public void updateMap(String roleName, Integer dest) {
         roleRepository.updateMap(roleName,dest);
    }




    /**
     * 根据角色名roleName获取地图id
     * @param roleName
     * @return
     */
    public int getMapIdByRoleName(String roleName) {
        return roleRepository.getMapIdByRoleName(roleName);
    }

    /**
     * 根据地图id获取地图name
     * @param map_id
     * @return
     */
    public String getMapNameByMapId(int map_id) {
        return roleRepository.getMapNameByMapId(map_id);
    }

    public ConcreteRole getRoleByRoleName(String rolename) {
        return roleRepository.getRoleByRoleName(rolename);
    }

    public void updateRole(ConcreteRole concreteRole) {
        roleRepository.updateRole(concreteRole);
    }

    public void insertRole(ConcreteRole role) {
        roleRepository.insertRole(role);
    }

    /**
     * 创建角色
     * @param channel
     * @param requestRoleInfo
     * @return
     */
    public MsgRoleInfoProto.ResponseRoleInfo chooseRole(Channel channel, MsgRoleInfoProto.RequestRoleInfo requestRoleInfo) {
        //userId
        long userId = LocalUserMap.getChannelUserMap().get(channel);
        //roleName
        String roleName = requestRoleInfo.getRoleName();
        //occupationId   职业id
        int occupationId = requestRoleInfo.getOccupationId();
        //获取user
        User user = userRepository.selectUserById((int) userId);
        //注册role
        registerRole.preRegister(user.getUsername(), roleName, occupationId);
        //获取role
        ConcreteRole role = roleRepository.getRoleByRoleName(roleName);
        //返回信息
       return MsgRoleInfoProto.ResponseRoleInfo.newBuilder()
                .setType(MsgRoleInfoProto.RequestType.CHOOSEROLE)
                .setContent(ContentType.CREATE_ROLE)
                .setResult(ResultCode.SUCCESS)
                .setRole(protoService.transToRole(role))
                .build();
    }

    /**
     * 获取角色信息
     * @param channel
     * @param requestRoleInfo
     * @return
     */
    public MsgRoleInfoProto.ResponseRoleInfo roleInfo(Channel channel, MsgRoleInfoProto.RequestRoleInfo requestRoleInfo) {
        //获取role
        ConcreteRole role = getRole(channel);
        //返回信息
        return MsgRoleInfoProto.ResponseRoleInfo.newBuilder()
                .setType(MsgRoleInfoProto.RequestType.ROLEINFO)
                .setResult(ResultCode.SUCCESS)
                .setRole(protoService.transToRole(role))
                .build();
    }

    public MsgRoleInfoProto.ResponseRoleInfo useGoods(Channel channel, MsgRoleInfoProto.RequestRoleInfo requestRoleInfo) {
        //role
        ConcreteRole tmpRole = getRole(channel);
        //goodsName
        String goodsName = requestRoleInfo.getGoodsName();
        //获取角色
        ConcreteRole role = MapUtils.getMapRolename_Role().get(tmpRole.getName());
        //获取角色的物品列表
        List<Goods> goodsList = backpackService.getGoodsByRoleId(role.getId());
        //获取角色的具体物品
        Goods goods = getGoods(goodsList,goodsName);
        //判断物品是否存在
        boolean isExisted = checkGoodsIsExist(goods);
        //判断物品的功能、使用物品
        String msg = checkAndUseGoodsFunction(role,goods,isExisted);
        //返回信息
        return MsgRoleInfoProto.ResponseRoleInfo.newBuilder()
                .setContent(msg)
                .setType(MsgRoleInfoProto.RequestType.USEGOODS)
                .build();
    }

    public ConcreteRole getRole(Channel channel){
        //userId
        Integer userId = LocalUserMap.getChannelUserMap().get(channel);
        //获取role
        ConcreteRole role = LocalUserMap.getUserRoleMap().get(userId);
        return role;
    }
    /**
     * 获取物品
     * @param goodsList
     * @param goodsName
     * @return
     */
    private Goods getGoods(List<Goods> goodsList, String goodsName) {
        Goods goods = null;
        for (int i = 0; i < goodsList.size(); i++) {
            if(goodsList.get(i).getName().equals(goodsName)){
                goods = goodsList.get(i);
            }
        }
        return goods;
    }
    /**
     * 检查物品是否存在
     * @param goods
     * @return
     */
    private boolean checkGoodsIsExist(Goods goods) {
        if(goods==null||goods.getCount()<=0){
            return false;
        }
        return true;
    }
    /**
     * 判断物品的功能、使用物品
     * @param role
     * @param goods
     * @param isExisted
     * @return
     */
    private String checkAndUseGoodsFunction(ConcreteRole role,Goods goods,boolean isExisted) {
        if(isExisted){
            //检查物品的类型
            Integer type = goods.getType();
            if(type>1){
                return "无法使用该物品,该物品既不是血包，也不是蓝包";
            }
            Goods localGoods = MapUtils.getGoodsMap().get(goods.getName());
            //检查物品是血包，还是蓝包（0：血包；1：蓝包）
            if(localGoods.getType()==0){
                //检查角色的血是否已满
                boolean isFull = checkFullBoold(role);
                if(isFull){
                    return "血已满，无需使用"+localGoods.getName();
                }
                //获取属性系统
                JSONObject property = localGoods.getProperty();
                String hp = property.getString("hp");
                Map<PropertyType, Integer> curMap = role.getCurMap();
                Integer oldHp = curMap.get(PropertyType.HP);
                curMap.put(PropertyType.HP,oldHp+Integer.parseInt(hp));
                //通知角色更新
                InjectRoleProperty.injectRoleProperty(role);
                System.out.println("加血后："+role.getCurHp());
                MapUtils.getMapRolename_Role().put(role.getName(),role);
            }else{
                boolean isFull = checkFullMagic(role);
                if(isFull){
                    return "蓝已满，无需使用"+localGoods.getName();
                }
                //获取属性系统
                Map<PropertyType, Integer> curMap = role.getCurMap();
                Integer mp = curMap.get(PropertyType.MP);
                JSONObject property = localGoods.getProperty();
                String oldMp = property.getString("mp");
                curMap.put(PropertyType.MP,mp+Integer.parseInt(oldMp));
                //通知角色更新
                InjectRoleProperty.injectRoleProperty(role);
                System.out.println("加蓝后："+role.getCurMp());
                MapUtils.getMapRolename_Role().put(role.getName(),role);
            }
            //更新物品数据库信息
            backpackService.updateGoodsByRoleIdDel(role.getId(),goods.getId());
            return "成功使用"+goods.getName();
        }else{
            return "物品不存在";
        }
    }
    private boolean checkFullBoold(ConcreteRole role) {
        return role.getCurHp()>=100?true:false;
    }
    private boolean checkFullMagic(ConcreteRole role) {
        return role.getCurMp()>=100?true:false;
    }
}
