package com.game.user.service;

import com.game.user.bean.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName UserService
 * @Description
 * @Author DELL
 * @Date 2019/7/3 16:52
 * @Version 1.0
 */
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    public void updateUser(User user) {
        userRepository.updateUser(user);
    }
}
