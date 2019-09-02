package com.game.gang.manager;

import com.game.gang.bean.JobResource;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName JobPermissionMap
 * @Description 职位-职位权限
 * @Author DELL
 * @Date 2019/7/16 14:57
 * @Version 1.0
 */
public class JobPermissionMap {
    /**
     * 职位和职位权限容器map
     */
    private static volatile Map<String, JobResource> jobPermissionMap = null;

    private JobPermissionMap(){}

    public static Map<String,JobResource> getJobPermissionMap(){
        if(jobPermissionMap==null){
            synchronized (JobPermissionMap.class){
                if(jobPermissionMap==null){
                    jobPermissionMap = new HashMap<>();
                }
            }
        }
        return jobPermissionMap;
    }
}
