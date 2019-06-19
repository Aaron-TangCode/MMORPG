package com.game.equipment.repository;

import com.game.equipment.bean.EquipmentBox;
import com.game.equipment.mapper.EquipmentMapper;
import com.game.utils.SqlUtils;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

/**
 * @ClassName EquipmentRepository
 * @Description TODO
 * @Author DELL
 * @Date 2019/6/19 15:14
 * @Version 1.0
 */
@Repository
public class EquipmentRepository {
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

    public void updateEquipment(EquipmentBox equipmentBox) {
        SqlSession session = SqlUtils.getSession();
        try{
            EquipmentMapper mapper = session.getMapper(EquipmentMapper.class);
           mapper.updateEquipment(equipmentBox.getRoleId(),equipmentBox.getEquipmentBox());
           session.commit();
        }finally {
            session.close();
        }
    }

    public void insertEquipment(EquipmentBox equipmentBox) {
        SqlSession session = SqlUtils.getSession();
        try{
            EquipmentMapper mapper = session.getMapper(EquipmentMapper.class);
           mapper.insertEquipment(equipmentBox.getRoleId(),equipmentBox.getEquipmentBox());
           session.commit();
        }finally {
            session.close();
        }
    }
}
