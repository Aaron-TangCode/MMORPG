package com.game.task.mapper;

import com.game.task.bean.RoleTask;

/**
 * @ClassName TaskMapper
 * @Description taskMapper
 * @Author DELL
 * @Date 2019/7/22 20:03
 * @Version 1.0
 */
public interface TaskMapper {
    /**
     * 查询任务
     * @param id id
     * @return 角色任务
     */
    public RoleTask queryTask(int id);

    /**
     * 更新任务
     * @param roleTask 角色任务
     */
    public void updateTask(RoleTask roleTask);
}
