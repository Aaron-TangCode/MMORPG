package com.game.protobuf.service;

import com.game.protobuf.protoc.RoleProto;
import com.game.role.bean.ConcreteRole;
import org.springframework.stereotype.Service;

/**
 * @ClassName ProtoService
 * @Description 协议服务
 * @Author DELL
 * @Date 2019/8/7 19:08
 * @Version 1.0
 */
@Service
public class ProtoService {
    /**
     * proto的role和自定义role转化
     * @param role role
     * @return role->protobuf's role
     */
    public RoleProto.Role transToRole(ConcreteRole role){

        return RoleProto.Role.newBuilder()
                .setId(role.getId())
                .setName(role.getName())
                .setLevel(role.getLevel())
                .setAttack(role.getAttack()==null?0:role.getAttack())
                .setDefend(role.getDefend()==null?0:role.getDefend())
                .setCurHp(role.getCurHp())
                .setCurMp(role.getCurMp())
                .setTotalHp(role.getTotalHp())
                .setTotalMp(role.getTotalMp())
                .setBackpackCapacity(role.getBackpackCapacity())
                .setMoney(role.getBackpackCapacity())
                .build();
    }
}
