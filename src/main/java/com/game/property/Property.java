package com.game.property;

/**
 * @ClassName Property
 * @Description 属性实体类
 * @Author DELL
 * @Date 2019/6/20 21:34
 * @Version 1.0
 */
public class Property {
    private int mp;
    private int hp;
    private int attack;
    private int defend;

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
}
