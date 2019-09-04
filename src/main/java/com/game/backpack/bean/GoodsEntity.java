package com.game.backpack.bean;

/**
 * @ClassName GoodsEntity
 * @Description 数据库实体类
 * @Author DELL
 * @Date 2019/9/4 11:19
 * @Version 1.0
 */
public class GoodsEntity {
    /**
     * 角色id
     */
    private String roleId;
    /**
     * 商品背包
     */
    private String goodsBag;

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getGoodsBag() {
        return goodsBag;
    }

    public void setGoodsBag(String goodsBag) {
        this.goodsBag = goodsBag;
    }
}
