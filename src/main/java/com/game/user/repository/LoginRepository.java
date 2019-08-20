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
     * @param username username
     * @param password password
     * @return true or false
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
     * @param username username
     * @return int
     */
    public int getUserRoleIdByUsername(String username) {
        SqlSession session = SqlUtils.getSession();

        try{
            UserMapper mapper = session.getMapper(UserMapper.class);
            Integer id = mapper.getUserRoleIdByUsername(username);
            if(id==null){
                return 0;
            }
            return id;

        }finally {
            session.close();
        }
    }

    /**
     * 检查用户是否存在
     * @param username username
     * @return User
     */
    public User checkUser(String username) {
        SqlSession session = SqlUtils.getSession();

        try{
            UserMapper mapper = session.getMapper(UserMapper.class);
            return mapper.checkUser(username);
        }finally {
            session.close();
        }
    }
}
