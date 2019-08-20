package com.game.task.bean;

import com.game.task.mapper.TaskMapper;
import com.game.utils.SqlUtils;
import org.apache.ibatis.session.SqlSession;

import java.util.concurrent.Callable;

/**
 * @ClassName UpdateTaskTask
 * @Description TODO
 * @Author DELL
 * @Date 2019/8/20 11:38
 * @Version 1.0
 */
public class UpdateTaskTask  implements Callable {
    private RoleTask roleTask;

    public UpdateTaskTask(RoleTask roleTask) {
        this.roleTask = roleTask;
    }

    @Override
    public Object call() throws Exception {
        SqlSession session = SqlUtils.getSession();
        try{
            TaskMapper mapper = session.getMapper(TaskMapper.class);
            mapper.updateTask(roleTask);
            session.commit();
        }finally {
            session.close();
        }
        return null;
    }
}
