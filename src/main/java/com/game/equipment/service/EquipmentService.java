package com.game.equipment.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.game.backpack.bean.Goods;
import com.game.backpack.service.BackpackService;
import com.game.equipment.bean.Equipment;
import com.game.equipment.bean.EquipmentBox;
import com.game.equipment.repository.EquipmentRepository;
import com.game.property.bean.PropertyType;
import com.game.property.manager.InjectProperty;
import com.game.protobuf.protoc.MsgEquipInfoProto;
import com.game.role.bean.ConcreteRole;
import com.game.role.manager.InjectRoleProperty;
import com.game.user.manager.LocalUserMap;
import com.game.utils.MapUtils;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @ClassName EquipmentService
 * @Description TODO
 * @Author DELL
 * @Date 2019/6/19 15:14
 * @Version 1.0
 */
@Service
public class EquipmentService {
    @Autowired
    private EquipmentRepository equipmentRepository;
    @Autowired
    private BackpackService backpackService;

    public EquipmentBox getEquipmet(int id) {
       return equipmentRepository.getEquipment(id);
    }

    public void updateEquipment(EquipmentBox equipmentBox) {
        equipmentRepository.updateEquipment(equipmentBox);
    }

    public void insertEquipment(EquipmentBox equipmentBox) {
        equipmentRepository.insertEquipment(equipmentBox);
    }

    /**
     * 添加装备到装备栏(插入或替换)
     * @param channel
     * @param requestEquipInfo
     * @return
     */
    public MsgEquipInfoProto.ResponseEquipInfo addEquip(Channel channel, MsgEquipInfoProto.RequestEquipInfo requestEquipInfo) {
        //获取角色
        ConcreteRole role = getRole(channel);
        //goodsName
        String goodsName = requestEquipInfo.getGoodsName();
        //获取角色拥有的装备
        EquipmentBox equipmentBox = role.getEquipmentBox();
        //获取玩家物品列表
        List<Goods> goodsList = backpackService.getGoodsByRoleId(role.getId());
        //获取具体装备
        Goods goods = handleEquipement(role,equipmentBox,goodsList,goodsName);
        //给goods注入propertyMap
        injectGoods(goods);
        handleProperty(role,goods,goodsList);
        String content = role.getName()+"成功把装备："+goodsName+"添加到装备栏";
        return MsgEquipInfoProto.ResponseEquipInfo.newBuilder()
                .setContent(content)
                .setType(MsgEquipInfoProto.RequestType.ADDEQUIP)
                .build();
    }
    /**
     * 处理属性模块
     * @param role
     * @param goods
     * @param goodsList from db
     */
    private void handleProperty(ConcreteRole role,Goods goods,List<Goods> goodsList) {
        //记录属性总值的临时变量
        Map<PropertyType, Integer> tmpTotalMap = role.getTotalMap();
        //属性模块发生变化
        //属性总值=装备属性+基础属性
        changeProperty(role,goods,goodsList);
        //属性当前值(HP,MP)随总值百分比变化
        changeMPHP(role,tmpTotalMap);
        //属性模块通知角色属性发生变化
        InjectRoleProperty.injectRoleProperty(role);
        //背包减少装备
        backpackService.updateGoodsByRoleIdDel(role.getId(),goods.getId());
    }
    private void injectGoods(Goods goods) {
        Goods newGoods = MapUtils.getGoodsMap().get(goods.getName());
        JSONObject jsonObject1 = newGoods.getProperty();
        Set<Map.Entry<String, Object>> entries = jsonObject1.entrySet();
        Iterator<Map.Entry<String, Object>> iterator = entries.iterator();
        Map<PropertyType, Integer> map = new HashMap<>();
        goods.setPropertyMap(map);
        while (iterator.hasNext()) {
            Map.Entry<String, Object> next = iterator.next();
            String key = next.getKey();
            String value = jsonObject1.getString(key);
            PropertyType propertyType = PropertyType.map.get(key);
            goods.getPropertyMap().put(propertyType,Integer.valueOf(value));
        }
    }
    private Goods  handleEquipement(ConcreteRole role,EquipmentBox equipmentBox,List<Goods> goodsList,String goodsName) {
        Goods goods = getGoods(goodsList,goodsName);
        //获取装备的类型
        String eName = EquipmentBox.getEquipMap().get(goods.getType());
        //选择装备，装配在装备栏
        //更新
        if(equipmentBox!=null){
            //json转实体类
            Equipment equipment = JSON.parseObject(equipmentBox.getEquipmentBox(),Equipment.class);
            //返回被替换的装备
            Goods goods1 = returnOldEquipmemt(eName,equipment);
            //设置新的装备栏（覆盖）
            JSON json = setEquipmentValueAndReturnJson(eName, equipment, goods);
            //把json设置在装备栏
            equipmentBox.setEquipmentBox(json.toJSONString());
            //更新装备栏
            updateEquipment(equipmentBox);
            Goods goods2 = MapUtils.getGoodsMap().get(goods1.getName());
            goods2.setRoleId(goods1.getRoleId());
            //把被替换装备放回背包
            backpackService.insertGoods(goods2);
        }else{
            //新建一个装备对象
            Equipment equipment = new Equipment();
            equipmentBox = new EquipmentBox();
            //绑定角色和装备栏
            equipmentBox.setRoleId(role.getId());
            //设置新的装备到装备栏
            JSON json = setEquipmentValueAndReturnJson(eName, equipment, goods);
            //把装备设置在装备栏
            equipmentBox.setEquipmentBox(json.toJSONString());
            //添加装备栏
            insertEquipment(equipmentBox);
        }
        return goods;
    }
    private Goods returnOldEquipmemt(String eName,Equipment equipment) {
        String goodsId = null;
        switch (eName) {
            case "head":
                goodsId = equipment.getHead();
                break;
            case "clothes":
                goodsId = equipment.getClothes();
                break;
            case "pants":
                goodsId = equipment.getPants();
                break;
            case "weapon":
                goodsId = equipment.getWeapon();
                break;
            case "shoes":
                goodsId = equipment.getShoes();
                break;
            default:
        }
        return backpackService.getGoodsById(goodsId);
    }

    /**
     * 按照百分比来改变MPHP
     * @param role
     * @param tmpTotalMap
     */
    private void changeMPHP(ConcreteRole role, Map<PropertyType, Integer> tmpTotalMap) {
        Map<PropertyType, Integer> totalMap = role.getTotalMap();
        Map<PropertyType, Integer> curMap = role.getCurMap();
        Integer newHp = totalMap.get(PropertyType.HP);
        Integer newMp = totalMap.get(PropertyType.MP);
        Integer oldHp = tmpTotalMap.get(PropertyType.HP);
        Integer oldMp = tmpTotalMap.get(PropertyType.MP);
        Integer curNewHp = curMap.get(PropertyType.HP);
        Integer curNewMp = curMap.get(PropertyType.MP);

        double percentHp = (double)newHp/(double)oldHp;
        double percentMp = (double)newMp/(double)oldMp;
        double curHp = (double)curNewHp*percentHp;
        double curMp = (double)curNewMp*percentMp;

        curMap.put(PropertyType.HP,(int)curHp);
        curMap.put(PropertyType.MP,(int)curMp);
    }

    /**
     * 改变属性总值和属性当前值(ATTACK和DEFEND)
     * @param role
     * @param goods
     */
    private void changeProperty(ConcreteRole role,Goods goods,List<Goods> goodsList) {
        EquipmentBox equipmentBox = role.getEquipmentBox();
        Equipment equipment = JSON.parseObject(equipmentBox.getEquipmentBox(),Equipment.class);
        List<Goods> list = InjectProperty.returnOwnEquipmentList(equipment, goodsList);
        //总值减去-装备属性=基础属性
        for (Goods goods2 : list) {
            //每一件装备的每一个属性
            for (Map.Entry<PropertyType,Integer>  entry:goods2.getPropertyMap().entrySet()) {
                // 拿出玩家属性，加上装备属性，放回去
                role.getTotalMap().put(
                        entry.getKey(),
                        role.getTotalMap().get(entry.getKey())-entry.getValue());
                if(entry.getKey().equals(PropertyType.ATTACK)||entry.getKey().equals(PropertyType.DEFEND)){
                    role.getCurMap().put(
                            entry.getKey(),
                            role.getCurMap().get(entry.getKey())-entry.getValue());
                }

            }
        }
        //添加装备栏的所有装备进list
        for (int i = 0; i < list.size(); i++) {
            if ((int)(list.get(i).getType())==(int)goods.getType()) {
                //移除旧装备
                list.remove(i);
                //添加新装备
                list.add(goods);
            }
        }
        for (Goods goods2 : list) {
            //每一件装备的每一个属性
            for (Map.Entry<PropertyType,Integer>  entry:goods2.getPropertyMap().entrySet()) {
                // 拿出玩家属性，加上装备属性，放回去
                role.getTotalMap().put(
                        entry.getKey(),
                        role.getTotalMap().get(entry.getKey())+entry.getValue());
                if(entry.getKey().equals(PropertyType.ATTACK)||entry.getKey().equals(PropertyType.DEFEND)) {
                    role.getCurMap().put(
                            entry.getKey(),
                            role.getCurMap().get(entry.getKey()) + entry.getValue());
                }
            }
        }
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
    public MsgEquipInfoProto.ResponseEquipInfo removeEquip(Channel channel, MsgEquipInfoProto.RequestEquipInfo requestEquipInfo) {
        return null;
    }
    public ConcreteRole getRole(Channel channel){
        Integer userId = LocalUserMap.getChannelUserMap().get(channel);
        ConcreteRole role = LocalUserMap.getUserRoleMap().get(userId);
        return role;
    }
}
