package com.game.equipment.observer;

import com.game.equipment.bean.EquipmentBox;

/**
 * @ClassName EquipmentObserver
 * @Description 装备观察者
 * @Author DELL
 * @Date 2019/6/19 11:29
 * @Version 1.0
 */
public abstract class EquipmentObserver {
    public EquipmentBox equipment;
    public abstract void update();
}
