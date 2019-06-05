package com.game.map;

/**
 * @ClassName ConcreteMap
 * @Description TODO
 * @Author DELL
 * @Date 2019/5/3015:09
 * @Version 1.0
 */
public class ConcreteMap {
    private int id;
    private String name;

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

    @Override
    public String toString() {
        return "ConcreteMap{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
