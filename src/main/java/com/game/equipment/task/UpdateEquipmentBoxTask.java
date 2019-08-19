package com.game.equipment.task;

import com.game.equipment.bean.EquipmentBox;
import com.game.equipment.mapper.EquipmentMapper;
import com.game.utils.SqlUtils;
import org.apache.ibatis.session.SqlSession;

import java.util.concurrent.Callable;

/**
 * @ClassName UpdateEquipmentBoxTask
 * @Description 更新装备栏任务
 * @Author DELL
 * @Date 2019/8/19 17:01
 * @Version 1.0
 */
public class UpdateEquipmentBoxTask implements Callable {
    private EquipmentBox equipmentBox;

    public UpdateEquipmentBoxTask(EquipmentBox equipmentBox) {
        this.equipmentBox = equipmentBox;
    }

    /**
     * 执行任务
     * @return object
     * @throws Exception 异常
     */
    @Override
    public Object call() throws Exception {
        SqlSession session = SqlUtils.getSession();
        try{
            EquipmentMapper mapper = session.getMapper(EquipmentMapper.class);
            mapper.updateEquipment(equipmentBox.getRoleId(),equipmentBox.getEquipmentBox());
            session.commit();
        }finally {
            session.close();
        }
        return null;
    }
}
