package com.game.service;

import com.game.dispatcher.RequestAnnotation;
import com.game.repository.LoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

/**
 * @ClassName Login
 * @Description TODO
 * @Author DELL
 * @Date 2019/5/2715:34
 * @Version 1.0
 */
@Component
public class Login {

    @Autowired
   private LoginRepository loginRepository;

    public boolean login(String username,String password){
        System.out.println("loginsevice");
        if(username!=null&&password!=null){
            return loginRepository.login(username,password);
        }else{
            return false;
        }
    }

    public int getUserRoleIdByUsername(String username) {
        return loginRepository.getUserRoleIdByUsername(username);
    }
}
