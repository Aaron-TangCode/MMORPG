package com.hailintang.gameserver2.map;

import com.hailintang.gameserver2.role.ParentRole;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName Forest
 * @Description TODO
 * @Author DELL
 * @Date 2019/5/2411:51
 * @Version 1.0
 */
public class Forest implements Map {
    private int id;
    private String name;
    private static List<ParentRole> list = new ArrayList<>();
    public Forest(int id,String name){
        this.id = id;
        this.name = name;
    }
    @Override
    public int getId() {
        return id;
    }
    @Override
    public String getName() {
        return name;
    }

    @Override
    public void display() {
        System.out.println("您目前在："+getName());
    }

    @Override
    public List<ParentRole> getList() {
        return list;
    }
}
