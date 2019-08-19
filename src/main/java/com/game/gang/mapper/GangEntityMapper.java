package com.game.gang.mapper;

import com.game.gang.bean.GangEntity;

/**
 * @ClassName GangEntityMapper
 * @Description 工会mapper
 * @Author DELL
 * @Date 2019/7/17 10:49
 * @Version 1.0
 */
public interface GangEntityMapper {
    /**
     * 插入工会
     * @param gangName 工会名称
     */
    public void insertGang(String gangName);

    /**
     * 查询工会（名字）
     * @param gangName 工会名
     * @return 工会
     */
    public GangEntity queryGang(String gangName);

    /**
     *  通过角色查询工会
     * @param roleId 角色id
     * @return
     */
    public GangEntity queryGangByRoleName(Integer roleId);
    /**
     * 更新工会
     * @param gangEntity 工会
     */
    public void updateGangEntity(GangEntity gangEntity);
}
