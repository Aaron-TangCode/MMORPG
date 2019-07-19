package com.game.gang.mapper;

import com.game.gang.bean.GangEntity;

/**
 * @ClassName GangEntityMapper
 * @Description TODO
 * @Author DELL
 * @Date 2019/7/17 10:49
 * @Version 1.0
 */
public interface GangEntityMapper {
    public void insertGang(String gangName);
    public GangEntity queryGang(String gangName);
    public GangEntity queryGangByRoleName(Integer roleId);
    public void updateGangEntity(GangEntity gangEntity);
}
