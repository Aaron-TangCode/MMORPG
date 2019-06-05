package com.game.State;

/**
 * @ClassName ConcreteState
 * @Description TODO
 * @Author DELL
 * @Date 2019/5/3015:13
 * @Version 1.0
 */
public class ConcreteState {
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
        return "ConcreteState{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
