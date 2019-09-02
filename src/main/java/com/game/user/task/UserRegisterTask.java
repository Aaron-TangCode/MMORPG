package com.game.user.task;

import com.game.user.mapper.UserMapper;
import com.game.user.bean.User;
import com.game.utils.SqlUtils;
import org.apache.ibatis.session.SqlSession;

/**
 * @ClassName UserRegisterTask
 * @Description 用户注册任务
 * @Author DELL
 * @Date 2019/6/6 16:24
 * @Version 1.0
 */
public class UserRegisterTask implements Runnable {
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 用户
     */
    private User user;

    public UserRegisterTask(String username, String password, User user) {
        this.username = username;
        this.password = password;
        this.user = user;
    }

    @Override
    public void run() {
        SqlSession session = SqlUtils.getSession();
        UserMapper mapper = session.getMapper(UserMapper.class);
        try {
            User newUser = new User();
            newUser.setUsername(username);
            newUser.setPassword(password);
            //注册用户
            mapper.addUser(newUser);
            session.commit();
        }finally {
            session.close();
        }

    }
}
