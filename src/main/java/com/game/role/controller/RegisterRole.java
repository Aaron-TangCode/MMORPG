package com.game.role.controller;

import com.game.dispatcher.RequestAnnotation;
import com.game.occupation.bean.Occupation;
import com.game.occupation.manager.OccupationMap;
import com.game.role.bean.ConcreteRole;
import com.game.role.service.RoleService;
import com.game.user.bean.User;
import com.game.user.service.Login;
import com.game.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * @ClassName RegisterRole
 * @Description 注册角色
 * @Author DELL
 * @Date 2019/7/3 15:59
 * @Version 1.0
 */
@RequestAnnotation("/register")
@Controller
public class RegisterRole {

    @Autowired
    private Login login;

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserService userService;
    @RequestAnnotation("/role")
    public String preRegister(String username,String roleName,Integer occupationId){
        //检查用户是否注册
        User user =  getUser(username);
      String msg = register(user,username,roleName,occupationId);
        return msg;
    }

    private String register(User user, String username, String roleName, Integer occupationId) {
        //注册角色和选择职业
        if(user!=null){
            //从缓存找职业
            Occupation occupation =  handle(user,roleName,occupationId);
            return roleName+"成功注册\t职业:"+occupation.getName() ;
        }else{
            return username+"还没注册";
        }
    }

    private User getUser(String username) {
        return login.checkUser(username);
    }

    private Occupation handle(User user,String roleName,Integer occupationId) {
        Occupation occupation = OccupationMap.getOccupationMap().get(occupationId);
        ConcreteRole role = new ConcreteRole();
        role.setName(roleName);
        role.setOccupation(occupation);
        roleService.insertRole(role);
        role = roleService.getRoleByRoleName(roleName);
        user.setRole(role);
        userService.updateUser(user);
        return occupation;
    }
}
