package com.game.equipment.mapper;

import com.game.equipment.bean.EquipmentBox;
import org.apache.ibatis.annotations.Param;

/**
 * @ClassName EquipmentMapper
 * @Description 装备mapper
 * @Author DELL
 * @Date 2019/6/19 12:13
 * @Version 1.0
 */
public interface EquipmentMapper {
    /**
     * 添加装备到装备栏
     * @param roleId 角色id
     * @param equipmentPart 装备
     */
    public void insertEquipment(@Param("roleId") int roleId, @Param("equipmentPart") String equipmentPart);

    /**
     * 修改装备
     * @param roleId 角色id
     * @param equipmentPart 装备
     */
    public void updateEquipment(@Param("roleId") int roleId, @Param("equipmentPart") String equipmentPart);

    /**
     * 查找装备
     * @param roleId 角色id
     * @return 装备栏
     */
    public EquipmentBox getEquipment(int roleId);
}
