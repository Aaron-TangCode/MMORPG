package com.game.equipment.observer;

/**
 * @ClassName Subject
 * @Description TODO
 * @Author DELL
 * @Date 2019/6/19 11:46
 * @Version 1.0
 */
public interface Subject {
    /**
     * 添加观察者
     */

    void addObserver(EquipmentObserver obj);
    /**
     * 移除观察者
     */
    void deleteObserver(EquipmentObserver obj);
    /**
     * 当主题方法改变时,这个方法被调用,通知所有的观察者
     */
    void notifyObserver();
}
