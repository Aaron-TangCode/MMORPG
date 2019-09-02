package com.game.user.task;

import com.game.user.bean.User;
import com.game.user.mapper.UserMapper;
import com.game.utils.SqlUtils;
import org.apache.ibatis.session.SqlSession;

import java.util.concurrent.Callable;

/**
 * @ClassName UpdateUserTask
 * @Description 更新User任务
 * @Author DELL
 * @Date 2019/8/20 11:55
 * @Version 1.0
 */
public class UpdateUserTask implements Callable {
    /**
     * 用户
     */
    private User user;

    public UpdateUserTask(User user) {
        this.user = user;
    }

    @Override
    public Object call() throws Exception {
        SqlSession session = SqlUtils.getSession();
        try{
            UserMapper mapper = session.getMapper(UserMapper.class);
            mapper.updateUser(user);
            session.commit();
        }finally {
            session.close();
        }
        return null;
    }
}
