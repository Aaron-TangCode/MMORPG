package com.game.user.repository;

import com.game.user.mapper.UserMapper;
import com.game.user.bean.User;
import com.game.user.task.UserRegisterTask;
import com.game.utils.SqlUtils;
import com.game.utils.ThreadPoolUtils;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

/**
 * @ClassName RegisterRepository
 * @Description 用户注册repository
 * @Author DELL
 * @Date 2019/5/2716:35
 * @Version 1.0
 */
@Repository("RegisterRepository")
public class RegisterRepository {
    /**
     * 用户注册
     * @param username username
     * @param password password
     * @return true or false
     */
    public boolean register(String username,String password) {
        SqlSession session = SqlUtils.getSession();
        try {

            UserMapper mapper = session.getMapper(UserMapper.class);
            //查找用户名是否存在
            User user = mapper.selectUserByUsername(username);
            //交给线程处理
            if (user == null) {
                UserRegisterTask task = new UserRegisterTask(username, password, user);
                ThreadPoolUtils.getThreadPool().execute(task);
                return true;
            } else {
                System.out.println(username + "已经存在");
                return false;
            }
        } finally {
            session.close();
        }
    }
}
