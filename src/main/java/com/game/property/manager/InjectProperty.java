package com.game.property.manager;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.game.backpack.bean.Goods;
import com.game.backpack.service.BackpackService;
import com.game.equipment.bean.Equipment;
import com.game.equipment.bean.EquipmentBox;
import com.game.equipment.service.EquipmentService;
import com.game.property.bean.PropertyType;
import com.game.role.bean.ConcreteRole;
import com.game.role.service.RoleService;
import com.game.utils.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @ClassName InjectProperty
 * @Description TODO
 * @Author DELL
 * @Date 2019/6/25 10:56
 * @Version 1.0
 */
@Component
public class InjectProperty {
    @Autowired
    private RoleService roleService;

    @Autowired
    private EquipmentService equipmentService;

    @Autowired
    private BackpackService backpackService;
    /**
     * 初始化属性总值
     * @param roleName
     */
    public void initProperty(String roleName) {
        //总值=基础值+装备值+环境值（暂无）
        Map<Integer, JSONObject> basicPropertyMap = ConcreteRole.getBasicPropertyMap();
        //根据角色名，在db上查找role
        ConcreteRole role = roleService.getRoleByRoleName(roleName);
        //获取角色等级
        int level = role.getLevel();
        //获取json
        JSONObject json = basicPropertyMap.get(level);
        //获取Key集合
        Set<String> keys = json.keySet();
        //迭代器
        Iterator<String> iterator = keys.iterator();
        //遍历,存储基础值
        while(iterator.hasNext()){
            //获取Key
            String key = iterator.next();
            //获取枚举相应类型
            PropertyType propertyType = PropertyType.map.get(key);
            //获取value
            String value = json.getString(key);
            //存储基础数据到总值
            role.getTotalMap().put(propertyType,Integer.parseInt(value));
        }
        //todo:注入装备栏属性
        //获取装备栏
        EquipmentBox equipmentBox = equipmentService.getEquipmet(role.getId());
        //获取装备
        Equipment equipment = JSON.parseObject(equipmentBox.getEquipmentBox(),Equipment.class);
        //数据格式："head":"1"--->装备类型：装备id
        List<Goods> goodsList = backpackService.getGoodsByRoleId(role.getId());

        String clothes = equipment.getClothes();
        String head = equipment.getHead();
        String pants = equipment.getPants();
        String shoes = equipment.getShoes();
        String weapon = equipment.getWeapon();
        List<Goods> ownEquipmentList = new ArrayList<>();

        //遍历装备栏的所有装备，并存在List列表
        for (int i = 0; i < goodsList.size(); i++) {
            if(head.equals(String.valueOf(goodsList.get(i).getId()))){
                Goods goods = MapUtils.getGoodsMap().get(goodsList.get(i).getName());
                ownEquipmentList.add(goods);
                System.out.println(ownEquipmentList.size());
            }else if(clothes.equals(String.valueOf(goodsList.get(i).getId()))){
                Goods goods = MapUtils.getGoodsMap().get(goodsList.get(i).getName());
                ownEquipmentList.add(goods);
            }else if(pants.equals(String.valueOf(goodsList.get(i).getId()))){
                Goods goods = MapUtils.getGoodsMap().get(goodsList.get(i).getName());
                ownEquipmentList.add(goods);
            }else if(shoes.equals(String.valueOf(goodsList.get(i).getId()))){
                Goods goods = MapUtils.getGoodsMap().get(goodsList.get(i).getName());
                ownEquipmentList.add(goods);
            }else if(weapon.equals(String.valueOf(goodsList.get(i).getId()))){
                Goods goods = MapUtils.getGoodsMap().get(goodsList.get(i).getName());
                ownEquipmentList.add(goods);
            }
        }

        //为ownEquipmentList的每一个goods对象注入propertyMap属性
        List<Goods> list = MapUtils.getGoodsList();
        for (int i = 0; i < ownEquipmentList.size(); i++) {
            if (ownEquipmentList.get(i).getName().equals(list.get(i).getName())) {
                ownEquipmentList.get(i).setPropertyMap(list.get(i).getPropertyMap());
            }
        }
        //每一件装备
        for (Goods goods : ownEquipmentList) {
            //每一件装备的每一个属性
            for (Map.Entry<PropertyType,Integer>  entry:goods.getPropertyMap().entrySet()) {
                // 拿出玩家属性，加上装备属性，放回去
                role.getTotalMap().put(
                        entry.getKey(),
                        role.getTotalMap().get(entry.getKey())+entry.getValue());
            }

        }
        //把总值复制到当前值
        Set<Map.Entry<PropertyType, Integer>> entrySet = role.getTotalMap().entrySet();
        Iterator<Map.Entry<PropertyType, Integer>> iterator1 = entrySet.iterator();
        while (iterator1.hasNext()) {
            Map.Entry<PropertyType, Integer> map = iterator1.next();
            role.getTotalMap().put(map.getKey(),map.getValue());
        }


        Set<Map.Entry<PropertyType, Integer>> entries = role.getTotalMap().entrySet();
        Iterator<Map.Entry<PropertyType, Integer>> iterator2 = entries.iterator();
        while (iterator2.hasNext()) {
            Map.Entry<PropertyType, Integer> next = iterator2.next();
            System.out.println(next.getKey()+";"+next.getValue());
        }

    }
}
