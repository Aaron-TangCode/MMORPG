package com.game.service;

import com.game.repository.RegisterRepository;
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

    public boolean register(String username,String password1,String passwoed2){
        if(username!=null&&password1!=null&&passwoed2!=null&&password1.equals(passwoed2)){
           return registerRepository.register(username,password1);
        }
        return false;
    }
}
