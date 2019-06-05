package com.game.user.repository;

import com.game.mapper.UserMapper;
import com.game.user.bean.User;
import com.game.utils.SqlUtils;
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
     * @param username
     * @param password
     * @return
     */
    public boolean register(String username,String password){
        SqlSession session = SqlUtils.getSession();
        try {

            UserMapper mapper = session.getMapper(UserMapper.class);
            //查找用户名是否存在
            User user = mapper.selectUserByUsername(username);
            if(user==null){
                User  new_user = new User();
                new_user.setUsername(username);
                new_user.setPassword(password);
                //注册用户
                mapper.addUser(new_user);
                session.commit();
                return true;
            }else{
                System.out.println(username+"已经存在");
                return false;
            }
        }finally {
            session.close();
        }
    }
}
