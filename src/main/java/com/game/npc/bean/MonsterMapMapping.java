package com.game.npc.bean;

/**
 * @ClassName MonsterMapMapping
 * @Description 怪物-地图映射实体类
 * @Author DELL
 * @Date 2019/6/11 17:46
 * @Version 1.0
 */
public class MonsterMapMapping {
    /**
     * 唯一id
     */
    private Integer id;
    /**
     * 怪兽id
     */
    private Integer monsterId;
    /**
     * 地图id
     */
    private Integer mapId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMonsterId() {
        return monsterId;
    }

    public void setMonsterId(Integer monsterId) {
        this.monsterId = monsterId;
    }

    public Integer getMapId() {
        return mapId;
    }

    public void setMapId(Integer mapId) {
        this.mapId = mapId;
    }
}
