package com.game.task.repository;

import com.game.task.bean.RoleTask;
import com.game.task.mapper.TaskMapper;
import com.game.utils.SqlUtils;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

/**
 * @ClassName TaskRepository
 * @Description TODO
 * @Author DELL
 * @Date 2019/7/22 17:37
 * @Version 1.0
 */
@Repository
public class TaskRepository {
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

    public void updateTask(RoleTask roleTask) {
        SqlSession session = SqlUtils.getSession();
        try{
            TaskMapper mapper = session.getMapper(TaskMapper.class);
            mapper.updateTask(roleTask);
            session.commit();
        }finally {
            session.close();
        }
    }
}
