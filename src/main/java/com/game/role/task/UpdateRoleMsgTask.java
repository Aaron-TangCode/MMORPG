package com.game.role.task;

import com.game.role.bean.ConcreteRole;
import com.game.role.service.RoleService;
import com.game.utils.CacheUtils;

import java.util.Map;
import java.util.Set;

/**
 * @ClassName UpdateRoleMsgTask
 * @Description 定时器更新数据库
 * @Author DELL
 * @Date 2019/8/26 22:11
 * @Version 1.0
 */
public class UpdateRoleMsgTask implements Runnable {

    private RoleService roleService;

    public UpdateRoleMsgTask(RoleService roleService) {

        this.roleService = roleService;
    }

    @Override
    public void run() {
        //获取缓存
        Map<String, ConcreteRole> roleMap = CacheUtils.getMapRolename_Role();

        Set<Map.Entry<String, ConcreteRole>> entries = roleMap.entrySet();
        //遍历更新
        for (Map.Entry<String, ConcreteRole> entry : entries) {
            ConcreteRole role = entry.getValue();
            roleService.updateRole(role);
        }
    }
}
