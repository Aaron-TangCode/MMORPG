package com.game.property.bean;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName PropertyType
 * @Description TODO
 * @Author DELL
 * @Date 2019/6/24 18:03
 * @Version 1.0
 */
public enum PropertyType {


    /**
     * HP:血量
     * MP:魔法值
     * ATTACK：攻击力
     * DEFEND:防御力
     */
    HP("hp"),MP("mp"),ATTACK("attack"),DEFEND("defend");


    String name;
    public static Map<String,PropertyType> map = new ConcurrentHashMap<>();

    PropertyType(String name) {
        this.name = name;
    }

    static {

        Arrays.stream(PropertyType.values())
                .forEach(
                    e -> map.put(e.name,e)
                );
    }
}
