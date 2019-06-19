package com.test.testmysql;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.game.equipment.bean.Equipment;
import com.game.equipment.bean.EquipmentBox;
import com.game.equipment.mapper.EquipmentMapper;
import com.game.utils.SqlUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

/**
 * @ClassName TestDemo01
 * @Description TODO
 * @Author DELL
 * @Date 2019/6/19 12:10
 * @Version 1.0
 */
public class TestDemo01 {
    @Test
    public void test01(){
        SqlSession session = SqlUtils.getSession();
        try{
            int role_id = 5;
            JSONObject json = new JSONObject(true);
            json.put("head",66);
            json.put("clothes",02);
            json.put("pants",03);
            json.put("weapon",04);
            json.put("shoes",05);
            EquipmentMapper mapper = session.getMapper(EquipmentMapper.class);

            EquipmentBox equipmentBox = mapper.getEquipment(role_id);
            System.out.println(equipmentBox.getEquipmentBox());

            Equipment equipment = JSON.parseObject(equipmentBox.getEquipmentBox(), Equipment.class);
            System.out.println(equipment);
            System.out.println();
        }finally {
            session.close();
        }
    }
}
