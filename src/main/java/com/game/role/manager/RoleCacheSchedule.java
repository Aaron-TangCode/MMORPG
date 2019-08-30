package com.game.role.manager;

import com.game.annotation.ExcelAnnotation;
import com.game.role.service.RoleService;
import com.game.role.task.UpdateRoleMsgTask;
import com.game.user.threadpool.UserThreadPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @ClassName CacheSchedule
 * @Description 刷新缓存定时器
 * @Author DELL
 * @Date 2019/8/26 22:13
 * @Version 1.0
 */
@Component
@ExcelAnnotation
public class RoleCacheSchedule {
    @Autowired
    private RoleService roleService;
    @ExcelAnnotation
    public void submit(){
        //创建任务
        UpdateRoleMsgTask updateRoleMsgTask = new UpdateRoleMsgTask(roleService);
        //执行任务
        UserThreadPool.ACCOUNT_SERVICE[0].scheduleAtFixedRate(updateRoleMsgTask,10L,5L, TimeUnit.SECONDS);
    }
}
