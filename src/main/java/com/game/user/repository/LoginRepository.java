package com.game.user.repository;

import com.game.user.bean.User;
import com.game.user.mapper.UserMapper;
import com.game.utils.SqlUtils;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

/**
 * @ClassName LoginRepository
 * @Description 用户登录repository
 * @Author DELL
 * @Date 2019/5/2716:34
 * @Version 1.0
 */
@Component
public class LoginRepository {
    /**
     * 检查登录
     * @param username
     * @param password
     * @return
     */
    public boolean login(String username,String password){
        SqlSession session = SqlUtils.getSession();

        try{
            UserMapper mapper = session.getMapper(UserMapper.class);
            User user = mapper.selectUserByUsername(username);
            return password.equals(user.getPassword());
        }finally {
            session.close();
        }
    }
    /**

     * 根据username找RoleId
     * @param username
     * @return
     */
    public int getUserRoleIdByUsername(String username) {
        SqlSession session = SqlUtils.getSession();

        try{
            UserMapper mapper = session.getMapper(UserMapper.class);
           return mapper.getUserRoleIdByUsername(username);
        }finally {
            session.close();
        }
    }
}
