package com.game.npc.bean;

/**
 * @ClassName ConcreteNPC
 * @Description NPC实体类
 * @Author DELL
 * @Date 2019/6/11 10:54
 * @Version 1.0
 */
public class ConcreteNPC {
    /**
     * 唯一id
     */
    private Integer id;
    /**
     * NPC名字
     */
    private String name;
    /**
     * NPC的说话内容
     */
    private String content;

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
