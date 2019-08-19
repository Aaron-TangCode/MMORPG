package com.game.gang.service;

import com.game.gang.bean.GangEntity;
import com.game.gang.bean.GangMemberEntity;
import com.game.gang.bean.Job;
import com.game.gang.repository.GangRepository;
import com.game.protobuf.protoc.MsgGangInfoProto;
import com.game.role.bean.ConcreteRole;
import com.game.role.service.RoleService;
import com.game.user.manager.LocalUserMap;
import com.game.utils.MapUtils;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;

/**
 * @ClassName GangService
 * @Description 工会服务
 * @Author DELL
 * @Date 2019/7/16 16:21
 * @Version 1.0
 */
@Service
public class GangService {
    /**
     * 工会数据访问
     */
    @Autowired
    private GangRepository gangRepository;
    /**
     * 角色服务
     */
    @Autowired
    private RoleService roleService;

    /**
     * 查找工会成员
     * @param roleId 角色id
     * @return 工会成员
     */
    public GangMemberEntity findGangMember(int roleId) {
        return gangRepository.findGangMember(roleId);
    }

    /**
     * 创建工会
     * @param gangName 工会名称
     */
    public void insertGang(String gangName) {
        gangRepository.insertGang(gangName);
    }

    /**
     * 查询工会
     * @param gangName 工会名称
     * @return 工会
     */
    public GangEntity queryGang(String gangName) {
        return gangRepository.queryGang(gangName);
    }

    /**
     * 添加工会成员
     * @param entity 工会
     */
    public void insertGangMember(GangMemberEntity entity) {
        gangRepository.insertGangMember(entity);
    }

    /**
     * 查询工会
     * @param roleId 角色id
     * @return
     */
    public GangEntity queryGangByRoleName(Integer roleId) {
        return gangRepository.queryGangByRoleName(roleId);
    }

    /**
     * 更新工会
     * @param gangEntity 工会
     */
    public void updateGangEntity(GangEntity gangEntity) {
        gangRepository.updateGangEntity(gangEntity);
    }

    /**
     * 创建工会
     * @param channel channel
     * @param requestGangInfo 工会请求信息
     * @return 协议信息
     */
    public MsgGangInfoProto.ResponseGangInfo createGang(Channel channel, MsgGangInfoProto.RequestGangInfo requestGangInfo) {
        //获取角色
        ConcreteRole role = getRole(channel);
        //判断该角色是否已经有工会
        GangMemberEntity flag = belongGang(role);
        //gangName
        String gangName = requestGangInfo.getGangName();
        //创建工会
        String content = buildGang(flag, gangName, role);

        return MsgGangInfoProto.ResponseGangInfo.newBuilder()
                .setContent(content)
                .setType(MsgGangInfoProto.RequestType.CREATEGANG)
                .build();
    }

    /**
     * 加入工会
     * @param channel channel
     * @param requestGangInfo 工会请求信息
     * @return 协议信息
     */
    public MsgGangInfoProto.ResponseGangInfo joinGang(Channel channel, MsgGangInfoProto.RequestGangInfo requestGangInfo) {
        //role
        ConcreteRole tmpRole = getRole(channel);
        //判断是否已经有工会
        GangMemberEntity entity = belongGang(tmpRole);
        //查询角色
        ConcreteRole role = getRoleByRoleName(tmpRole.getName());
        //gangName
        String gangName = requestGangInfo.getGangName();
        //加入工会
        String content = join(entity,role,gangName);

        return MsgGangInfoProto.ResponseGangInfo.newBuilder()
                .setContent(content)
                .setType(MsgGangInfoProto.RequestType.JOINGANG)
                .build();
    }
    /**
     * 加入工会
     * @param entity 工会
     * @param role 角色
     * @param gangName 工会名字
     */
    private String join(GangMemberEntity entity,ConcreteRole role,String gangName) {
        //没的话加入工会
        if(entity==null){
            GangMemberEntity newEntity = new GangMemberEntity();
            newEntity.setRole(role);
            //查询工会
            GangEntity gangEntity =  queryGang(gangName);
            newEntity.setGang(gangEntity);
            //设置工会职位
            newEntity.setJob(Job.GENERAL.getName());
            insertGangMember(newEntity);
            //返回信息
           return  "成功加入工会"+gangName;
        }else{//有就返回提示信息
           return  "你已经加入工会";
        }
    }

    /**
     * 解散工会
     * @param channel channel
     * @param requestGangInfo 工会请求信息
     * @return 协议信息
     */
    public MsgGangInfoProto.ResponseGangInfo dismissGang(Channel channel, MsgGangInfoProto.RequestGangInfo requestGangInfo) {
        return null;
    }

    /**
     * 捐款
     * @param channel channel
     * @param requestGangInfo 工会请求信息
     * @return 协议信息
     */
    public MsgGangInfoProto.ResponseGangInfo donateMoney(Channel channel, MsgGangInfoProto.RequestGangInfo requestGangInfo) {
        //获取工会
        ConcreteRole role = getRole(channel);
        GangEntity gangEntity = queryGangByRoleName(role.getId());
        //number
        String number = requestGangInfo.getNumber();
        int cost = Integer.parseInt(number);
        Integer money = role.getMoney();
        role.setMoney(money-cost);
        //更新角色
        roleService.updateRole(role);
        gangEntity.setFunds(gangEntity.getFunds()+cost);
        //更新工会基金
        updateGangEntity(gangEntity);
        String outputContent = "【通知】：{0}向{1}捐献{2}金币";
        String content = MessageFormat.format(outputContent,role.getName(),gangEntity.getName(),number);

        return MsgGangInfoProto.ResponseGangInfo.newBuilder()
                .setType(MsgGangInfoProto.RequestType.DONATEMONEY)
                .setContent(content)
                .build();
    }

    /**
     * 获取角色
     * @param channel channel
     * @return 角色
     */
    public ConcreteRole getRole(Channel channel){
        Integer userId = LocalUserMap.getChannelUserMap().get(channel);
        ConcreteRole role = LocalUserMap.getUserRoleMap().get(userId);
        return role;
    }
    /**
     * 判断角色是否有工会
     * @param role 角色
     * @return true or false
     */
    public GangMemberEntity belongGang(ConcreteRole role){
        GangMemberEntity entity = findGangMember(role.getId());
        return entity;
    }
    /**
     * 创建工会
     * @param flag 工会成员
     * @param gangName 工会名字
     * @param role 角色
     */
    private String buildGang(GangMemberEntity flag,String gangName,ConcreteRole role) {
        //没工会可创建工会
        if(flag==null){
            //插入工会
            insertGang(gangName);
            //查询工会
            GangEntity gangEntity =  queryGang(gangName);
            //插入工会会长
            GangMemberEntity entity = new GangMemberEntity();

            entity.setRole(role);
            entity.setGang(gangEntity);
            entity.setJob(Job.CHARIMEN.getName());
            insertGangMember(entity);
            return "成功创建工会";
        }else{//有工会就返回提示信息
            return role.getName()+"已经有工会了!\t工会："+flag.getGang().getName();
        }
    }
    /**
     * 获取角色
     * @return 角色
     */
    public ConcreteRole getRoleByRoleName(String roleName){
        return MapUtils.getMapRolename_Role().get(roleName);
    }
}
