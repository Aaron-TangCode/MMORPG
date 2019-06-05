package com.game.user.service;

import com.game.user.repository.RegisterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName RegisterService
 * @Description TODO
 * @Author DELL
 * @Date 2019/5/2715:34
 * @Version 1.0
 */
@Service("RegisterService")
public class RegisterService {
    @Autowired
    private RegisterRepository registerRepository;

    /**
     * 用户注册
     * @param username
     * @param password1
     * @param passwoed2
     * @return
     */
    public boolean register(String username,String password1,String passwoed2){
        if(username!=null&&password1!=null&&passwoed2!=null&&password1.equals(passwoed2)){
           return registerRepository.register(username,password1);
        }
        return false;
    }
}
