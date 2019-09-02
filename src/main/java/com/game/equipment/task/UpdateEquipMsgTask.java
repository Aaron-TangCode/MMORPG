package com.game.equipment.task;

import com.game.equipment.bean.EquipmentBox;
import com.game.equipment.service.EquipmentService;
import com.game.role.bean.ConcreteRole;
import com.game.utils.CacheUtils;

import java.util.Map;
import java.util.Set;

/**
 * @ClassName UpdateEquipMsgTask
 * @Description 装备缓存器
 * @Author DELL
 * @Date 2019/8/29 20:58
 * @Version 1.0
 */
public class UpdateEquipMsgTask implements Runnable {
    /**
     * 装备栏
     */
    private EquipmentService equipmentService;

    public UpdateEquipMsgTask(EquipmentService equipmentService) {
        this.equipmentService = equipmentService;
    }

    @Override
    public void run() {
        Map<String, ConcreteRole> roleMap = CacheUtils.getMapRoleNameRole();

        Set<Map.Entry<String, ConcreteRole>> entrySet = roleMap.entrySet();

        for (Map.Entry<String, ConcreteRole> entry : entrySet) {
            EquipmentBox equipmentBox = entry.getValue().getEquipmentBox();
            if(equipmentBox!=null){
                equipmentService.updateEquipment(equipmentBox);
            }
        }
    }
}
