package com.game.duplicate.mapper;

import org.apache.ibatis.annotations.Param;

/**
 * @ClassName TeamMapper
 * @Description TeamMapper
 * @Author DELL
 * @Date 2019/8/23 14:25
 * @Version 1.0
 */
public interface TeamMapper {
    /**
     * addTeam
     * @param teamName teamName
     * @param roleId roleId
     */
    public void addTeam(@Param("teamName") String teamName,@Param("roleId") Integer roleId);
}
