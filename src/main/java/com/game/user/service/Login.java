package com.game.user.service;

import com.game.user.bean.User;
import com.game.user.repository.LoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @ClassName Login
 * @Description 用户登录service
 * @Author DELL
 * @Date 2019/5/2715:34
 * @Version 1.0
 */
@Component
public class Login {

    @Autowired
   private LoginRepository loginRepository;

    /**
     * 用户登录
     * @param username username
     * @param password password
     * @return true or false
     */
    public boolean login(String username,String password){
        if(username!=null&&password!=null){
            return loginRepository.login(username,password);
        }else{
            return false;
        }
    }

    /**
     * 根据用户名username找角色role
     * @param username username
     * @return int
     */
    public int getUserRoleIdByUsername(String username) {
        return loginRepository.getUserRoleIdByUsername(username);
    }

    public User checkUser(String username) {
        return loginRepository.checkUser(username);
    }
}
