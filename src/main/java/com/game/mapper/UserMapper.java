package com.game.mapper;

import com.game.user.bean.User;

/**
 * @ClassName UserMapper
 * @Description TODO
 * @Author DELL
 * @Date 2019/5/2716:53
 * @Version 1.0
 */
public interface UserMapper {
    public User selectUserById(Integer id);
    public User selectUserByUsername(String username);
    public Integer getUserRoleIdByUsername(String username);
    public boolean addUser(User user);
}