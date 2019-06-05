package com.game.role;

import com.game.State.ConcreteState;
import com.game.map.ConcreteMap;
import org.springframework.context.annotation.Bean;

/**
 * @ClassName ConcreteRole
 * @Description TODO
 * @Author DELL
 * @Date 2019/5/3015:14
 * @Version 1.0
 */
public class ConcreteRole {
    private int id;
    private String name;
    private ConcreteMap concreteMap;
    private ConcreteState concreteState;
    private int hp = 100;//血量

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ConcreteMap getConcreteMap() {
        return concreteMap;
    }

    public void setConcreteMap(ConcreteMap concreteMap) {
        this.concreteMap = concreteMap;
    }

    public ConcreteState getConcreteState() {
        return concreteState;
    }

    public void setConcreteState(ConcreteState concreteState) {
        this.concreteState = concreteState;
    }

    public String getState(){
       return getHp()>0?"生存":"死亡";
    }
}
