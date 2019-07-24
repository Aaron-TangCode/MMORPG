package com.game.task.mapper;

import com.game.task.bean.RoleTask;

/**
 * @ClassName TaskMapper
 * @Description TODO
 * @Author DELL
 * @Date 2019/7/22 20:03
 * @Version 1.0
 */
public interface TaskMapper {
    public RoleTask queryTask(int id);
    public void updateTask(RoleTask roleTask);
}
