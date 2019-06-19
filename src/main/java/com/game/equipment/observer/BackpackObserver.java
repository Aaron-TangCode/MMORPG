package com.game.equipment.observer;

import com.game.equipment.bean.EquipmentBox;

/**
 * @ClassName BackpackObserver
 * @Description TODO
 * @Author DELL
 * @Date 2019/6/19 11:33
 * @Version 1.0
 */
public class BackpackObserver extends EquipmentObserver {
    public BackpackObserver(EquipmentBox equipment){
        this.equipment = equipment;
        this.equipment.addObserver(this);
    }
    @Override
    public void update() {

    }
}
