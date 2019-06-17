package com.game.backpack.bean;

/**
 * @ClassName Type
 * @Description 商品种类
 * @Author DELL
 * @Date 2019/6/17 18:29
 * @Version 1.0
 */
public class Type {
    /**
     * 类型唯一id
     */
    private Integer id;
    /**
     * 类型名字
     */
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
