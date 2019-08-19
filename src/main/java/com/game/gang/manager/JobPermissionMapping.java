package com.game.gang.manager;

import com.game.gang.bean.Job;
import com.game.gang.bean.JobPermission;
import com.game.gang.bean.JobResource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName JobPermissionMapping
 * @Description 职位-职位权限映射关系
 * @Author DELL
 * @Date 2019/7/16 15:04
 * @Version 1.0
 */
public class JobPermissionMapping {
    public static Map<String, List<JobPermission>> jobPermissionListMap = new HashMap<>();

    public static void handleRelation(Map<String, JobResource> jobPermissionMap){
        //遍历map，匹配job和permision
        for (Map.Entry<String, JobResource> stringJobResourceEntry : jobPermissionMap.entrySet()) {
            //list
            List<JobPermission> jobPermissionList = new ArrayList<>();
            String[] pmns = stringJobResourceEntry.getValue().getPermission().split(",");
            for (int i = 0; i < pmns.length; i++) {
                if(pmns[i].equalsIgnoreCase(JobPermission.HANDLE_APPLICATION.toString())){
                    jobPermissionList.add(JobPermission.HANDLE_APPLICATION);
                }else if(pmns[i].equalsIgnoreCase(JobPermission.DISMISS.toString())){
                    jobPermissionList.add(JobPermission.DISMISS);
                }else if(pmns[i].equalsIgnoreCase(JobPermission.EXIT.toString())){
                    jobPermissionList.add(JobPermission.EXIT);
                }else if(pmns[i].equalsIgnoreCase(JobPermission.KICK.toString())){
                    jobPermissionList.add(JobPermission.KICK);
                }
            }

            String job = stringJobResourceEntry.getKey();

            if(job.equalsIgnoreCase(Job.CHARIMEN.getName())){
                jobPermissionListMap.put(Job.CHARIMEN.getName(),jobPermissionList);
            }else if(job.equalsIgnoreCase(Job.VICE_CHARIMEN.getName())){
                jobPermissionListMap.put(Job.VICE_CHARIMEN.getName(),jobPermissionList);
            }else if(job.equalsIgnoreCase(Job.ELITE.getName())){
                jobPermissionListMap.put(Job.ELITE.getName(),jobPermissionList);
            }else if(job.equalsIgnoreCase(Job.GENERAL.getName())){
                jobPermissionListMap.put(Job.GENERAL.getName(),jobPermissionList);
            }
        }
    }

    public static Map<String, List<JobPermission>> getJobPermissionListMap(){
        return jobPermissionListMap;
    }
}
