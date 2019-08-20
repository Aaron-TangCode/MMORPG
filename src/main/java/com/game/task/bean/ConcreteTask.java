package com.game.task.bean;

import com.game.utils.QuestType;

/**
 * @ClassName ConcreteTask
 * @Description 任务模块
 * @Author DELL
 * @Date 2019/7/22 15:41
 * @Version 1.0
 */
public class ConcreteTask {
    /**
     * 任务id
     */
    private Integer id;
    /**
     * 任务描述
     */
    private String taskDescription;
    /**
     * 任务完成条件
     */
    private String condition;
    /**
     * 任务奖励
     */
    private Integer bonus;
    /**
     * 任务类型
     */
    private String type;
    /**
     * 任务类型
     */
    private QuestType questType;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public Integer getBonus() {
        return bonus;
    }

    public void setBonus(Integer bonus) {
        this.bonus = bonus;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public QuestType getQuestType() {
        return questType;
    }

    public void setQuestType(QuestType questType) {
        this.questType = questType;
    }
}
