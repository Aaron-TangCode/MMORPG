package com.game.equipment.bean;



import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName EquipmentBox
 * @Description 装备栏（实际是一个Map容器）
 * @Author DELL
 * @Date 2019/6/18 20:04
 * @Version 1.0
 */
public class EquipmentBox {
    private int id;
    private int roleId;
    private String equipmentBox;
    private static volatile Map<Integer, String> equipMap = null;
    public static Map<Integer,String> getEquipMap(){
        if(equipMap==null){
            synchronized (EquipmentBox.class){
                if(equipMap==null){
                    equipMap = new HashMap<>();
                    equipMap.put(2,"weapon");
                    equipMap.put(3,"head");
                    equipMap.put(4,"shoes");
                    equipMap.put(5,"clothes");
                    equipMap.put(6,"pants");
                }
            }
        }
        return equipMap;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getEquipmentBox() {
        return equipmentBox;
    }

    public void setEquipmentBox(String equipmentBox) {
        this.equipmentBox = equipmentBox;
    }


}
