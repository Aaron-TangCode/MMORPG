package com.game.equipment.observer;

import com.game.equipment.bean.EquipmentBox;

/**
 * @ClassName PropertyObserver
 * @Description TODO
 * @Author DELL
 * @Date 2019/6/19 11:32
 * @Version 1.0
 */
public class PropertyObserver extends EquipmentObserver {
    public PropertyObserver(EquipmentBox equipment){
        this.equipment = equipment;
        this.equipment.addObserver(this);
    }
    @Override
    public void update() {

    }
}
