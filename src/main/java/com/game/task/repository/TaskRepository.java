package com.game.task.repository;

import com.game.task.bean.RoleTask;
import com.game.task.bean.UpdateTaskTask;
import com.game.task.mapper.TaskMapper;
import com.game.user.threadpool.UserThreadPool;
import com.game.utils.SqlUtils;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

/**
 * @ClassName TaskRepository
 * @Description 任务数据访问
 * @Author DELL
 * @Date 2019/7/22 17:37
 * @Version 1.0
 */
@Repository
public class TaskRepository {
    /**
     * 查询任务
     * @param id id
     * @return 角色任务
     */
    public RoleTask queryTask(int id) {
        SqlSession session = SqlUtils.getSession();
        try{
            TaskMapper mapper = session.getMapper(TaskMapper.class);
            RoleTask roleTask = mapper.queryTask(id);
            return roleTask;
        }finally {
            session.close();
        }
    }
    /**
     * 更新任务
     * @param roleTask 角色任务
     */
    public void updateTask(RoleTask roleTask) {
        UpdateTaskTask updateTaskTask = new UpdateTaskTask(roleTask);
        UserThreadPool.ACCOUNT_SERVICE[0].submit(updateTaskTask);
    }
}
