package com.game.map.bean;

/**
 * @ClassName MapMapping
 * @Description 地图映射实体类
 * @Author DELL
 * @Date 2019/6/315:29
 * @Version 1.0
 */
public class MapMapping {
    /**
     * 地图id
     */
    private Integer id;
    /**
     * 源地图
     */
    private Integer srcMap;
    /**
     * 目标地图
     */
    private Integer destMap;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSrcMap() {
        return srcMap;
    }

    public void setSrcMap(Integer srcMap) {
        this.srcMap = srcMap;
    }

    public Integer getDestMap() {
        return destMap;
    }

    public void setDestMap(Integer destMap) {
        this.destMap = destMap;
    }

    @Override
    public String toString() {
        return "MapMapping{" +
                "id=" + id +
                ", srcMap=" + srcMap +
                ", destMap=" + destMap +
                '}';
    }
}
