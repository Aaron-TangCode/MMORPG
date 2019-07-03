package com.game.user.service;

import com.game.user.bean.User;
import com.game.user.mapper.UserMapper;
import com.game.utils.SqlUtils;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

/**
 * @ClassName UserRepository
 * @Description TODO
 * @Author DELL
 * @Date 2019/7/3 17:07
 * @Version 1.0
 */
@Repository
public class UserRepository {

    public void updateUser(User user) {
        SqlSession session = SqlUtils.getSession();
        try{
            UserMapper mapper = session.getMapper(UserMapper.class);
            mapper.updateUser(user);
            session.commit();
        }finally {
            session.close();
        }
    }
}
