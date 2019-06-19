package com.game.equipment.service;

import com.game.equipment.bean.EquipmentBox;
import com.game.equipment.repository.EquipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName EquipmentService
 * @Description TODO
 * @Author DELL
 * @Date 2019/6/19 15:14
 * @Version 1.0
 */
@Service
public class EquipmentService {
    @Autowired
    private EquipmentRepository equipmentRepository;

    public EquipmentBox getEquipmet(int id) {
       return equipmentRepository.getEquipment(id);
    }

    public void updateEquipment(EquipmentBox equipmentBox) {
        equipmentRepository.updateEquipment(equipmentBox);
    }

    public void insertEquipment(EquipmentBox equipmentBox) {
        equipmentRepository.insertEquipment(equipmentBox);
    }
}
