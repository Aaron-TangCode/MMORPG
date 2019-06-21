package com.game.equipment.controller;

import com.alibaba.fastjson.JSON;
import com.game.backpack.bean.Goods;
import com.game.backpack.service.BackpackService;
import com.game.dispatcher.RequestAnnotation;
import com.game.equipment.bean.Equipment;
import com.game.equipment.bean.EquipmentBox;
import com.game.equipment.service.EquipmentService;
import com.game.property.bean.Property;
import com.game.property.manager.PropertyManager;
import com.game.role.bean.ConcreteRole;
import com.game.utils.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @ClassName EquipmentController
 * @Description TODO
 * @Author DELL
 * @Date 2019/6/18 21:11
 * @Version 1.0
 */
@RequestAnnotation("/equipment")
@Component
public class EquipmentController {
    @Autowired
    private EquipmentService equipmentService;

    @Autowired
    private BackpackService backpackService;

    @RequestAnnotation("/addEquipment")
    public String addEquipment(String roleName,String goodsName){
        //获取角色
        ConcreteRole role = getRole(roleName);
        //获取角色拥有的装备
        EquipmentBox equipmentBox = equipmentService.getEquipmet(role.getId());

        //获取玩家物品列表
        List<Goods> goodsList = backpackService.getGoodsByRoleId(role.getId());
        //获取具体装备
        Goods goods = getGoods(goodsList,goodsName);
        String eName = EquipmentBox.getEquipMap().get(goods.getType());
        //选择装备，装配在装备栏
        if(equipmentBox!=null){
            Equipment equipment = JSON.parseObject(equipmentBox.getEquipmentBox(),Equipment.class);
            //
            JSON json = setEquipmentValueAndReturnJson(eName, equipment, goods);
            //
            equipmentBox.setEquipmentBox(json.toJSONString());
            //更新装备栏
           equipmentService.updateEquipment(equipmentBox);
        }else{
            Equipment equipment = new Equipment();
            equipmentBox = new EquipmentBox();
            equipmentBox.setRoleId(role.getId());
            JSON json = setEquipmentValueAndReturnJson(eName, equipment, goods);
            //这里需要json
            equipmentBox.setEquipmentBox(json.toJSONString());
            //添加装备栏
            equipmentService.insertEquipment(equipmentBox);
        }

        //获取角色等级
        int level = role.getLevel();
        //获取角色属性
        Property property = PropertyManager.getMap().get(level);
        //根据角色的装备类型去增加相应属性
        property.changeProperty(goods);
        //通知角色属性发生变化
        adviseRole(role,property);
        //背包减少装备
        backpackService.updateGoodsByRoleIdDel(role.getId(),goods.getId());
        return roleName+"成功把装备："+goodsName+"添加到装备栏";
    }

    private void adviseRole(ConcreteRole role,Property property) {
        role.setMp();
        role.setAttack();
        role.setDefend();
        role.setHp();
    }


    private JSON setEquipmentValueAndReturnJson(String eName,Equipment equipment,Goods goods) {
        switch (eName){
            case "head":
                equipment.setHead(String.valueOf(goods.getId()));
                break;
            case "clothes":
                equipment.setClothes(String.valueOf(goods.getId()));
                break;
            case "pants":
                equipment.setPants(String.valueOf(goods.getId()));
                break;
            case "weapon":
                equipment.setWeapon(String.valueOf(goods.getId()));
                break;
            case "shoes":
                equipment.setShoes(String.valueOf(goods.getId()));
                break;
           default:
               break;
        }
        JSON json = (JSON) JSON.toJSON(equipment);
        return json;
    }


    /**
     * 获取商品
     * @param goodsList
     * @param goodsName
     * @return
     */
    private Goods getGoods(List<Goods> goodsList, String goodsName) {
        Goods goods = null;
        //遍历物品
        for (int i = 0; i < goodsList.size(); i++) {
            if(goodsList.get(i).getName().equals(goodsName)){
                goods = goodsList.get(i);
            }
        }
        return goods;
    }

    private void getEquipments(ConcreteRole role) {

    }

    private ConcreteRole getRole(String roleName) {
         return MapUtils.getMapRolename_Role().get(roleName);
    }

    @RequestAnnotation("/removeEquipment")
    public String removeEquipment(String roleName,String goodsName){
        //获取角色信息

        //获取角色的装备栏的装备

        //装备从装备栏移除

        //角色属性发生变化

        //装备信息，落地数据库

        //背包增加一个装备

        return null;
    }
}
