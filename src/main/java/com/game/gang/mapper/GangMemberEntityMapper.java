package com.game.gang.mapper;

import com.game.gang.bean.GangMemberEntity;

public interface GangMemberEntityMapper {
    /**
     * 查询工会成员
     * @param roleId
     * @return
     */
    public GangMemberEntity findGangMember(Integer roleId);
}
