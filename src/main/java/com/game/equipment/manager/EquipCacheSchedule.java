package com.game.equipment.manager;

import com.game.annotation.ExcelAnnotation;
import com.game.equipment.service.EquipmentService;
import com.game.equipment.task.UpdateEquipMsgTask;
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
public class EquipCacheSchedule {
    @Autowired
    private EquipmentService equipmentService;
    @ExcelAnnotation
    public void submit(){
        //创建任务
        UpdateEquipMsgTask updateEquipMsgTask = new UpdateEquipMsgTask(equipmentService);
        //执行任务
        UserThreadPool.ACCOUNT_SERVICE[1].scheduleAtFixedRate(updateEquipMsgTask,10L,5L, TimeUnit.SECONDS);
    }
}
