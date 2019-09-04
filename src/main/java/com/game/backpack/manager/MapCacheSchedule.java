package com.game.backpack.manager;

import com.game.annotation.ExcelAnnotation;
import com.game.backpack.service.BackpackService;
import com.game.backpack.task.UpdateMapMsgTask;
import com.game.user.threadpool.UserThreadPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @ClassName MapCacheSchedule
 * @Description 刷新缓存定时器
 * @Author DELL
 * @Date 2019/8/26 22:13
 * @Version 1.0
 */
@Component
@ExcelAnnotation
public class MapCacheSchedule {
    @Autowired
    private BackpackService backpackService;
    @ExcelAnnotation
    public void submit(){
        //创建任务
        UpdateMapMsgTask updateMapMsgTask = new UpdateMapMsgTask(backpackService);
        //执行任务
        UserThreadPool.ACCOUNT_SERVICE[1].scheduleAtFixedRate(updateMapMsgTask,10L,5L, TimeUnit.SECONDS);
    }
}
