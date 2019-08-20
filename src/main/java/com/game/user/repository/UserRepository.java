package com.game.user.repository;

import com.game.user.bean.User;
import com.game.user.mapper.UserMapper;
import com.game.user.task.UpdateUserTask;
import com.game.user.threadpool.UserThreadPool;
import com.game.utils.SqlUtils;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

/**
 * @ClassName UserRepository
 * @Description 用户数据访问
 * @Author DELL
 * @Date 2019/7/3 17:07
 * @Version 1.0
 */
@Repository
public class UserRepository {
    /**
     * 更新user
     * @param user user
     */
    public void updateUser(User user) {
        UpdateUserTask updateUserTask = new UpdateUserTask(user);
        UserThreadPool.ACCOUNT_SERVICE[0].submit(updateUserTask);
    }

    /**
     *  查询用户
     * @param username username
     * @return user
     */
    public User selectUserByUsername(String username){
        SqlSession session = SqlUtils.getSession();
        try{
            UserMapper mapper = session.getMapper(UserMapper.class);
            return mapper.selectUserByUsername(username);
        }finally {
            session.close();
        }
    }

    /**
     * 查询用户
     * @param id id
     * @return user
     */
    public User selectUserById(int id){
        SqlSession session = SqlUtils.getSession();
        try{
            UserMapper mapper = session.getMapper(UserMapper.class);
            return mapper.selectUserById(id);
        }finally {
            session.close();
        }
    }
}
