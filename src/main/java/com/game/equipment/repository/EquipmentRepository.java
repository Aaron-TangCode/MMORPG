package com.game.equipment.repository;

import com.game.equipment.bean.EquipmentBox;
import com.game.equipment.mapper.EquipmentMapper;
import com.game.equipment.task.InsertEquipmentBoxTask;
import com.game.equipment.task.UpdateEquipmentBoxTask;
import com.game.user.threadpool.UserThreadPool;
import com.game.utils.SqlUtils;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

/**
 * @ClassName EquipmentRepository
 * @Description 装备repository
 * @Author DELL
 * @Date 2019/6/19 15:14
 * @Version 1.0
 */
@Repository
public class EquipmentRepository {
    /**
     * 获取装备栏
     * @param id
     * @return
     */
    public EquipmentBox getEquipment(int id) {
        SqlSession session = SqlUtils.getSession();
        try{
            EquipmentMapper mapper = session.getMapper(EquipmentMapper.class);
            EquipmentBox equipmentBox = mapper.getEquipment(id);
            return equipmentBox;
        }finally {
            session.close();
        }
    }

    /**
     * 更新装备栏
     * @param equipmentBox 装备栏
     */
    public void updateEquipment(EquipmentBox equipmentBox) {
        UpdateEquipmentBoxTask updateEquipmentBoxTask = new UpdateEquipmentBoxTask(equipmentBox);
        UserThreadPool.ACCOUNT_SERVICE[0].submit(updateEquipmentBoxTask);
    }

    /**
     * 添加装备到装备栏
     * @param equipmentBox 装备栏
     */
    public void insertEquipment(EquipmentBox equipmentBox) {
        InsertEquipmentBoxTask insertEquipmentBoxTask = new InsertEquipmentBoxTask(equipmentBox);
        UserThreadPool.ACCOUNT_SERVICE[0].submit(insertEquipmentBoxTask);
    }
}
