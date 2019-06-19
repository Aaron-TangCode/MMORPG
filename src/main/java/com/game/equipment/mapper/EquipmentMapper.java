package com.game.equipment.mapper;

import com.game.equipment.bean.EquipmentBox;
import org.apache.ibatis.annotations.Param;

/**
 * @ClassName EquipmentMapper
 * @Description TODO
 * @Author DELL
 * @Date 2019/6/19 12:13
 * @Version 1.0
 */
public interface EquipmentMapper {
    /**
     * 添加装备到装备栏
     * @param roleId
     * @param equipmentPart
     */
    public void insertEquipment(@Param("roleId") int roleId, @Param("equipmentPart") String equipmentPart);

    /**
     * 修改装备
     * @param roleId
     * @param equipmentPart
     */
    public void updateEquipment(@Param("roleId") int roleId, @Param("equipmentPart") String equipmentPart);

    /**
     * 查找装备
     * @param roleId
     * @return
     */
    public EquipmentBox getEquipment(int roleId);
}
