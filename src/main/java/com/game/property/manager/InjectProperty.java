package com.game.property.manager;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.game.backpack.bean.GoodsResource;
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
import com.game.utils.CacheUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @ClassName InjectProperty
 * @Description 注入属性
 * @Author DELL
 * @Date 2019/6/25 10:56
 * @Version 1.0
 */
@Component
public class InjectProperty {
    /**
     * 注入任务数据
     */
    @Autowired
    private InjectTaskData injectTaskData;
    /**
     * 角色服务
     */
    @Autowired
    private RoleService roleService;
    /**
     * 装备服务
     */
    @Autowired
    private EquipmentService equipmentService;
    /**
     * 背包服务
     */
    @Autowired
    private BackpackService backpackService;

    public static int count = 1;
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
        ConcreteRole role = CacheUtils.getRole(roleDB.getName());
        //检查role
        if(role==null){
            CacheUtils.addRole(roleName,roleDB);
            return;
        }
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
            role.getTotalStatMap().put(propertyType,Integer.parseInt(value));
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
            Map<PropertyType, Integer> totalMap = role.getTotalStatMap();
            //存进去
            totalMap.put(propertyType,totalMap.get(propertyType)+Integer.parseInt(value));
        }
        //todo:注入装备栏属性
        //获取装备栏
        EquipmentBox equipmentBox = equipmentService.getEquipmet(roleDB.getId());
        role.setEquipmentBox(equipmentBox);
        //校验
        if(equipmentBox!=null){
            //获取装备
            Equipment equipment = JSON.parseObject(equipmentBox.getEquipmentBox(),Equipment.class);
            //数据格式："head":"1"--->装备类型：装备id
            List<GoodsResource> goodsList = backpackService.getGoodsByRoleId(role.getId());

            List<GoodsResource> ownEquipmentList = returnOwnEquipmentList(equipment, goodsList);

            //每一件装备
            for (GoodsResource goods : ownEquipmentList) {
                //每一件装备的每一个属性
                for (Map.Entry<PropertyType,Integer>  entry:goods.getPropertyMap().entrySet()) {
                    // 拿出玩家属性，加上装备属性，放回去
                    role.getTotalStatMap().put(
                            entry.getKey(),
                            role.getTotalStatMap().get(entry.getKey())+entry.getValue());
                }

            }
        }

        //把总值复制到当前值
        Set<Map.Entry<PropertyType, Integer>> entrySet = role.getTotalStatMap().entrySet();
        Iterator<Map.Entry<PropertyType, Integer>> iterator1 = entrySet.iterator();
        if(count++==1){
            while (iterator1.hasNext()) {
                Map.Entry<PropertyType, Integer> map = iterator1.next();
                role.getCurStatMap().put(map.getKey(),map.getValue());
            }
        }else{
            while (iterator1.hasNext()) {
                Map.Entry<PropertyType, Integer> map = iterator1.next();
                if(map.getKey().name().equals("HP")){
                    role.getCurStatMap().put(map.getKey(),role.getCurHp());
                }else if(map.getKey().name().equals("MP") ){
                    role.getCurStatMap().put(map.getKey(),role.getCurMp());
                }else{
                    role.getCurStatMap().put(map.getKey(),map.getValue());
                }

            }
        }

        //把属性模块的数据注入角色模块
        InjectRoleProperty.injectRoleProperty(role);
        //刷新role的缓存
        CacheUtils.addRole(role.getName(), role);
    }


    /**
     * 返回装备列表
     * @param equipment 装备
     * @param goodsList 物品列表
     * @return 物品列表
     */
    public static List<GoodsResource> returnOwnEquipmentList(Equipment equipment, List<GoodsResource> goodsList) {
        String clothes = equipment.getClothes();
        String head = equipment.getHead();
        String pants = equipment.getPants();
        String shoes = equipment.getShoes();
        String weapon = equipment.getWeapon();
        List<GoodsResource> ownEquipmentList = new ArrayList<>();

        //遍历装备栏的所有装备，并存在List列表
        for (int i = 0; i < goodsList.size(); i++) {
            if(head!=null&&head.equals(String.valueOf(goodsList.get(i).getId()))){
                GoodsResource goods = CacheUtils.getGoodsMap().get(goodsList.get(i).getName());
                ownEquipmentList.add(goods);
                System.out.println(ownEquipmentList.size());
            }else if(clothes!=null&&clothes.equals(String.valueOf(goodsList.get(i).getId()))){
                GoodsResource goods = CacheUtils.getGoodsMap().get(goodsList.get(i).getName());
                ownEquipmentList.add(goods);
            }else if(pants!=null&&pants.equals(String.valueOf(goodsList.get(i).getId()))){
                GoodsResource goods = CacheUtils.getGoodsMap().get(goodsList.get(i).getName());
                ownEquipmentList.add(goods);
            }else if(shoes!=null&&shoes.equals(String.valueOf(goodsList.get(i).getId()))){
                GoodsResource goods = CacheUtils.getGoodsMap().get(goodsList.get(i).getName());
                ownEquipmentList.add(goods);
            }else if(weapon!=null&&weapon.equals(String.valueOf(goodsList.get(i).getId()))){
                GoodsResource goods = CacheUtils.getGoodsMap().get(goodsList.get(i).getName());
                ownEquipmentList.add(goods);
            }
        }

        //为ownEquipmentList的每一个goods对象注入propertyMap属性
        List<GoodsResource> list = CacheUtils.getGoodsList();
        for (int i = 0; i < ownEquipmentList.size(); i++) {
            if (ownEquipmentList.get(i).getName().equals(list.get(i).getName())) {
                ownEquipmentList.get(i).setPropertyMap(list.get(i).getPropertyMap());
            }
        }
        return ownEquipmentList;
    }
}
