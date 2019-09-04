package com.game.role.service;

import com.alibaba.fastjson.JSONObject;
import com.game.backpack.bean.GoodsResource;
import com.game.backpack.service.BackpackService;
import com.game.event.beanevent.GoodsEvent;
import com.game.event.manager.EventMap;
import com.game.occupation.bean.Occupation;
import com.game.occupation.manager.OccupationMap;
import com.game.property.bean.PropertyType;
import com.game.property.manager.InjectProperty;
import com.game.protobuf.message.ContentType;
import com.game.protobuf.message.ResultCode;
import com.game.protobuf.protoc.MsgRoleInfoProto;
import com.game.protobuf.service.ProtoService;
import com.game.role.bean.ConcreteRole;
import com.game.role.manager.InjectRoleProperty;
import com.game.role.repository.RoleRepository;
import com.game.user.bean.User;
import com.game.user.manager.LocalUserMap;
import com.game.user.repository.UserRepository;
import com.game.user.service.Login;
import com.game.user.service.UserService;
import com.game.utils.CacheUtils;
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
@Service
public class RoleService {

    /**
     * 用户服务
     */
    @Autowired
    private UserService userService;
    /**
     * 登录服务
     */
    @Autowired
    private Login login;
    /**
     * role数据访问
     */
    @Autowired
    private RoleRepository roleRepository;
    /**
     * user数据访问
     */
    @Autowired
    private UserRepository userRepository;
    /**
     * 背包服务
     */
    @Autowired
    private BackpackService backpackService;
    /**
     * proto服务
     */
    @Autowired
    private ProtoService protoService;
    /**
     * 物品事件
     */
    @Autowired
    private GoodsEvent goodsEvent;
    /**
     * 事件容器map
     */
    @Autowired
    private EventMap eventMap;
    /**
     * 注入属性
     */
    @Autowired
    private InjectProperty injectProperty;
    /**
     * 获取角色role
     * @param id id
     * @return role
     */
    public ConcreteRole getRole(int id){
        return roleRepository.getRole(id);
    }

    /**
     * 根据角色名roleName获取地图map
     * @param roleName roleName
     * @return map
     */
    public String getMapByRoleName(String roleName) {
        return roleRepository.getMapByRoleName(roleName);
    }

    /**
     * 更新角色所在的地图
     * @param roleName rolename
     * @param dest dest
     */
    public void updateMap(String roleName, Integer dest) {
         roleRepository.updateMap(roleName,dest);
    }




    /**
     * 根据角色名roleName获取地图id
     * @param roleName rolename
     * @return int
     */
    public int getMapIdByRoleName(String roleName) {
        return roleRepository.getMapIdByRoleName(roleName);
    }

    /**
     * 根据地图id获取地图name
     * @param map_id the id of map
     * @return map's name
     */
    public String getMapNameByMapId(int map_id) {
        return roleRepository.getMapNameByMapId(map_id);
    }

    /**
     * 获取role
     * @param rolename rolename
     * @return role
     */
    public ConcreteRole getRoleByRoleName(String rolename) {
        return roleRepository.getRoleByRoleName(rolename);
    }

    /**
     * 更新角色
     * @param concreteRole role
     */
    public void updateRole(ConcreteRole concreteRole) {
        roleRepository.updateRole(concreteRole);
    }

    /**
     * 插入role
     * @param role role
     */
    public void insertRole(ConcreteRole role) {
        roleRepository.insertRole(role);
    }

    /**
     * 创建角色
     * @param channel channel
     * @param requestRoleInfo 角色请求信息
     * @return 协议信息
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
        preRegister(user.getUsername(), roleName, occupationId);
        //获取role
        ConcreteRole role = roleRepository.getRoleByRoleName(roleName);
        LocalUserMap.getUserRoleMap().put((int)userId,role);
        //注入属性
        injectProperty.initProperty(role.getName());
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
     * @param channel channel
     * @param requestRoleInfo 角色请求信息
     * @return 协议信息
     */
    public MsgRoleInfoProto.ResponseRoleInfo roleInfo(Channel channel, MsgRoleInfoProto.RequestRoleInfo requestRoleInfo) {
        //获取role
        ConcreteRole tmpRole = getRole(channel);
        ConcreteRole role = CacheUtils.getMapRoleNameRole().get(tmpRole.getName());
        injectProperty.initProperty(role.getName());
        //返回信息
        return MsgRoleInfoProto.ResponseRoleInfo.newBuilder()
                .setType(MsgRoleInfoProto.RequestType.ROLEINFO)
                .setResult(ResultCode.SUCCESS)
                .setRole(protoService.transToRole(role))
                .build();
    }

    /**
     * 使用物品
     * @param channel channel
     * @param requestRoleInfo 角色请求信息
     * @return 协议信息
     */
    public MsgRoleInfoProto.ResponseRoleInfo useGoods(Channel channel, MsgRoleInfoProto.RequestRoleInfo requestRoleInfo) {
        //role
        ConcreteRole tmpRole = getRole(channel);
        //goodsName
        String goodsName = requestRoleInfo.getGoodsName();
        //获取角色
        ConcreteRole role = CacheUtils.getMapRoleNameRole().get(tmpRole.getName());
        //获取角色的物品列表
        List<GoodsResource> goodsList = backpackService.getGoodsByRoleId(role.getId());
        //获取角色的具体物品
        GoodsResource goods = getGoods(goodsList,goodsName);
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

    /**
     * 获取role
     * @param channel channel
     * @return role
     */
    public ConcreteRole getRole(Channel channel){
        //userId
        Integer userId = LocalUserMap.getChannelUserMap().get(channel);
        //获取role
        ConcreteRole role = LocalUserMap.getUserRoleMap().get(userId);
        return role;
    }
    /**
     * 获取物品
     * @param goodsList 物品列表
     * @param goodsName 物品名称
     * @return 物品
     */
    private GoodsResource getGoods(List<GoodsResource> goodsList, String goodsName) {
        GoodsResource goods = null;
        for (int i = 0; i < goodsList.size(); i++) {
            if(goodsList.get(i).getName().equals(goodsName)){
                goods = goodsList.get(i);
            }
        }
        return goods;
    }
    /**
     * 检查物品是否存在
     * @param goods 物品
     * @return true or false
     */
    private boolean checkGoodsIsExist(GoodsResource goods) {
        if(goods==null||goods.getCount()<=0){
            return false;
        }
        return true;
    }
    /**
     * 判断物品的功能、使用物品
     * @param role role
     * @param goods 物品
     * @param isExisted true or false
     * @return 协议信息
     */
    private String checkAndUseGoodsFunction(ConcreteRole role, GoodsResource goods, boolean isExisted) {
        if(isExisted){
            //检查物品的类型
            Integer type = goods.getType();
            if(type>1){
                return "无法使用该物品,该物品既不是血包，也不是蓝包";
            }
            GoodsResource localGoods = CacheUtils.getGoodsMap().get(goods.getName());
            //检查物品是血包，还是蓝包（0：血包；1：蓝包）
            if(localGoods.getType()==0){
                //检查角色的血是否已满
                boolean isFull = checkFullBoold(role);
                if(isFull){
                    return "血已满，无需使用"+localGoods.getName();
                }
                //获取属性系统
                JSONObject property = localGoods.getProperty();
                //获取Hp
                String hp = property.getString("hp");
                //获取当前的属性map
                Map<PropertyType, Integer> curMap = role.getCurMap();
                //获取旧的hp
                Integer oldHp = curMap.get(PropertyType.HP);
                int tmpHp = oldHp+Integer.parseInt(hp);
                int setHp = tmpHp>=role.getTotalHp()?role.getTotalHp():tmpHp;
                curMap.put(PropertyType.HP,setHp);
                //通知角色更新
                InjectRoleProperty.injectRoleProperty(role);
                CacheUtils.getMapRoleNameRole().put(role.getName(),role);
                backpackService.updateGoodsByRoleIdDel(role.getId(),goods.getId());
                //激活事件
                goodsEvent.setRole(role);
                eventMap.submit(goodsEvent);
                //返回消息
                return "成功使用"+goods.getName()+"\t加血后："+role.getCurHp();
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
                int tmpMp =Integer.parseInt(oldMp)+mp;
                int setMp = tmpMp>=role.getTotalMp()?role.getTotalMp():tmpMp;
                curMap.put(PropertyType.MP,setMp);
                //通知角色更新
                InjectRoleProperty.injectRoleProperty(role);
                CacheUtils.getMapRoleNameRole().put(role.getName(),role);
                backpackService.updateGoodsByRoleIdDel(role.getId(),goods.getId());
                return "成功使用"+goods.getName()+"\t加蓝后："+role.getCurMp();
            }
        }else{
            return "物品不存在";
        }
    }

    /**
     * 检查是否满血
     * @param role role
     * @return true or false
     */
    private boolean checkFullBoold(ConcreteRole role) {
        return role.getCurHp()>=role.getTotalHp()?true:false;
    }

    /**
     * 检查是否满蓝
     * @param role role
     * @return true or false
     */
    private boolean checkFullMagic(ConcreteRole role) {
        return role.getCurMp()>=role.getTotalMp()?true:false;
    }

    /**
     * 检查用户是否注册
     * @param username 用户名
     * @param roleName 角色名
     * @param occupationId 职业id
     * @return true or false
     */
    public String preRegister(String username,String roleName,Integer occupationId){
        //检查用户是否注册
        User user =  getUser(username);
        String msg = register(user,username,roleName,occupationId);
        return msg;
    }

    /**
     * 注册
     * @param user 用户
     * @param username 用户名
     * @param roleName 角色名
     * @param occupationId 职业id
     * @return 消息
     */
    private String register(User user, String username, String roleName, Integer occupationId) {
        //注册角色和选择职业
        if(user!=null){
            //从缓存找职业
            Occupation occupation =  handle(user,roleName,occupationId);
            return roleName+"成功注册\t职业:"+occupation.getName() ;
        }else{
            return username+"还没注册";
        }
    }

    /**
     * 获取用户
     * @param username 用户名
     * @return user
     */
    private User getUser(String username) {
        return login.checkUser(username);
    }

    /**
     * 处理
     * @param user user
     * @param roleName username
     * @param occupationId 职业id
     * @return 职业
     */
    private Occupation handle(User user,String roleName,Integer occupationId) {
        //获取职业
        Occupation occupation = OccupationMap.getOccupationMap().get(occupationId);
        //获取角色
        ConcreteRole role = new ConcreteRole();
        //注入属性
        role.setName(roleName);
        role.setOccupation(occupation);
        //添加角色
        insertRole(role);
        role = getRoleByRoleName(roleName);
        //角色绑定
        user.setRole(role);
        //更新用户
        userService.updateUser(user);
        return occupation;
    }
}
