package com.game.event.mapper;

import org.apache.ibatis.annotations.Param;

/**
 * @ClassName RoleCountMapper
 * @Description TODO
 * @Author DELL
 * @Date 2019/7/24 17:30
 * @Version 1.0
 */
public interface RoleCountMapper {
    public int queryCount(int roleId);
    public void updateCount(@Param("count") int count, @Param("roleId") int roleId);
    public void insert(@Param("roleId") int roleId, @Param("count") int count);
}
