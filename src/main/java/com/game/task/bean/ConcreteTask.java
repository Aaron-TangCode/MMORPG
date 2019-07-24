package com.game.task.bean;

/**
 * @ClassName ConcreteTask
 * @Description TODO
 * @Author DELL
 * @Date 2019/7/22 15:41
 * @Version 1.0
 */
public class ConcreteTask {
    private Integer id;
    private String taskDescription;
    private String condition;
    private Integer bonus;

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
}
