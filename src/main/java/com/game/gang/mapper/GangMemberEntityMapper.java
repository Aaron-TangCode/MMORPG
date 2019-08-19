package com.game.gang.mapper;

import com.game.gang.bean.GangMemberEntity;

public interface GangMemberEntityMapper {
    /**
     * 查询工会成员
     * @param roleId 角色id
     * @return 工会成员
     */
    public GangMemberEntity findGangMember(Integer roleId);

    /**
     * 添加工会成员
     * @param entity 插入工会成员
     */
    public void insertGangMember(GangMemberEntity entity);
}
