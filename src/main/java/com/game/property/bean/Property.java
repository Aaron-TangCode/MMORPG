package com.game.property.bean;

import com.game.backpack.bean.Goods;
import org.springframework.stereotype.Component;

/**
 * @ClassName Property
 * @Description 属性实体类
 * @Author DELL
 * @Date 2019/6/20 21:34
 * @Version 1.0
 */
@Component
public class Property {
    /**
     * 等级
     */
    private int level;
    /**
     * 魔法值
     */
    private int mp;
    /**
     * 生命值
     */
    private int hp;
    /**
     * 攻击值
     */
    private int attack;
    /**
     * 防御值
     */
    private int defend;


    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getMp() {
        return mp;
    }

    public void setMp(int mp) {
        this.mp = mp;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getDefend() {
        return defend;
    }

    public void setDefend(int defend) {
        this.defend = defend;
    }

    public void changeProperty(Goods goods) {
        setNewValue(goods);
    }

    /**
     * 装备属性+基础属性
     * @param goods
     */
    private void setNewValue(Goods goods) {

        int newAttack = Integer.parseInt(goods.getAttack())+getAttack();
        int newDefend = getDefend()+Integer.parseInt(goods.getDefend());
        int newHp = getHp()+Integer.parseInt(goods.getHp());
        int newMp = getMp()+Integer.parseInt(goods.getMp());
        setHp(newHp);
        setMp(newMp);
        setAttack(newAttack);
        setDefend(newDefend);
    }
}
