package com.game.property.manager;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.game.backpack.bean.Goods;
import com.game.backpack.service.BackpackService;
import com.game.equipment.bean.Equipment;
import com.game.equipment.bean.EquipmentBox;
import com.game.equipment.service.EquipmentService;
import com.game.occupation.bean.Occupation;
import com.game.occupation.manager.OccupationMap;
import com.game.property.bean.PropertyType;
import com.game.role.bean.ConcreteRole;
import com.game.role.manager.InjectRoleProperty;
import com.game.role.service.RoleService;
import com.game.task.manager.InjectTaskData;
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
    private InjectTaskData injectTaskData;
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
        //总值=基础值+装备值+职业值+环境值（暂无）
        Map<Integer, JSONObject> basicPropertyMap = ConcreteRole.getBasicPropertyMap();
        //根据角色名，在db上查找role
        ConcreteRole roleDB = roleService.getRoleByRoleName(roleName);
        //从本地获取role
        ConcreteRole role = MapUtils.getMapRolename_Role().get(roleDB.getName());
        //注入任务数据
        injectTaskData.injectData(role);
        //注入角色的技能属性
        role.setConcreteSkill(roleDB.getConcreteSkill());
        //获取角色等级
        int level = roleDB.getLevel();
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
        //todo:注入职业值
        //获取角色职业
        Occupation occupation = OccupationMap.getOccupationMap().get(role.getOccupation().getId());
        //获取属性值（json格式）
        JSONObject jsonObject = occupation.getProperty();
        Set<String> keySet = jsonObject.keySet();
        Iterator<String> iterator2 = keySet.iterator();
        //遍历,存储职业值
        while (iterator2.hasNext()) {
            //获取key
            String key = iterator2.next();
            PropertyType propertyType = PropertyType.map.get(key);
            String value = jsonObject.getString(key);
            Map<PropertyType, Integer> totalMap = role.getTotalMap();
            //存进去
            totalMap.put(propertyType,totalMap.get(propertyType)+Integer.parseInt(value));
        }
        //todo:注入装备栏属性
        //获取装备栏
        EquipmentBox equipmentBox = equipmentService.getEquipmet(roleDB.getId());
        role.setEquipmentBox(equipmentBox);
        //获取装备
        Equipment equipment = JSON.parseObject(equipmentBox.getEquipmentBox(),Equipment.class);
        //数据格式："head":"1"--->装备类型：装备id
        List<Goods> goodsList = backpackService.getGoodsByRoleId(role.getId());

        List<Goods> ownEquipmentList = returnOwnEquipmentList(equipment, goodsList);

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
            role.getCurMap().put(map.getKey(),map.getValue());
        }
        //把属性模块的数据注入角色模块
        InjectRoleProperty.injectRoleProperty(role);
        //刷新role的缓存
        MapUtils.getMapRolename_Role().put(role.getName(), role);
    }

    public static List<Goods> returnOwnEquipmentList(Equipment equipment,List<Goods> goodsList) {
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
        return ownEquipmentList;
    }
}
