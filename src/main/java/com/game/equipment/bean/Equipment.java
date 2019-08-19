package com.game.equipment.bean;

/**
 * @ClassName Equipment
 * @Description 装备实体类
 * @Author DELL
 * @Date 2019/6/19 14:59
 * @Version 1.0
 */
public class Equipment {
    /**
     * 头部
     */
    private String head;
    /**
     * 衣服
     */
    private String clothes;
    /**
     * 裤子
     */
    private String pants;
    /**
     * 装备
     */
    private String weapon;
    /**
     * 鞋子
     */
    private String shoes;

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getClothes() {
        return clothes;
    }

    public void setClothes(String clothes) {
        this.clothes = clothes;
    }

    public String getPants() {
        return pants;
    }

    public void setPants(String pants) {
        this.pants = pants;
    }

    public String getWeapon() {
        return weapon;
    }

    public void setWeapon(String weapon) {
        this.weapon = weapon;
    }

    public String getShoes() {
        return shoes;
    }

    public void setShoes(String shoes) {
        this.shoes = shoes;
    }

    @Override
    public String toString() {
        return "Equipment{" +
                "head='" + head + '\'' +
                ", clothes='" + clothes + '\'' +
                ", pants='" + pants + '\'' +
                ", weapon='" + weapon + '\'' +
                ", shoes='" + shoes + '\'' +
                '}';
    }
}
