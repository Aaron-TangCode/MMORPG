package com.game.gang.bean;

/**
 * @ClassName Job
 * @Description 职位
 * @Author DELL
 * @Date 2019/7/16 12:12
 * @Version 1.0
 */
public enum  Job {
    /**
     * 工会会长
     */
    CHARIMEN("会长"),VICE_CHARIMEN("副会长"), ELITE("精英"), GENERAL("普通成员");

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    Job(String name){
        this.name = name;
    }


}
