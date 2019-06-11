package com.game.npc.bean;

/**
 * @ClassName MapNPCMapping
 * @Description 地图和NPC映射类
 * @Author DELL
 * @Date 2019/6/11 12:24
 * @Version 1.0
 */
public class MapNPCMapping {
    /**
     * 唯一id
     */
   private Integer id;
    /**
     * 地图id
     */
   private Integer mapId;
    /**
     * npcId
     */
   private Integer npcId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMapId() {
        return mapId;
    }

    public void setMapId(Integer mapId) {
        this.mapId = mapId;
    }

    public Integer getNpcId() {
        return npcId;
    }

    public void setNpcId(Integer npcId) {
        this.npcId = npcId;
    }
}
