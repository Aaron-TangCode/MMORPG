package com.game.role.service;

import com.game.protobuf.message.ContentType;
import com.game.protobuf.message.ResultCode;
import com.game.protobuf.protoc.MsgRoleInfoProto;
import com.game.protobuf.service.ProtoService;
import com.game.role.bean.ConcreteRole;
import com.game.role.controller.RegisterRole;
import com.game.role.repository.RoleRepository;
import com.game.user.bean.User;
import com.game.user.manager.LocalUserMap;
import com.game.user.repository.UserRepository;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        //userId
        Integer userId = LocalUserMap.getChannelUserMap().get(channel);
        //获取role
        ConcreteRole role = LocalUserMap.getUserRoleMap().get(userId);
        //返回信息
        return MsgRoleInfoProto.ResponseRoleInfo.newBuilder()
                .setType(MsgRoleInfoProto.RequestType.ROLEINFO)
                .setResult(ResultCode.SUCCESS)
                .setRole(protoService.transToRole(role))
                .build();
    }
}
