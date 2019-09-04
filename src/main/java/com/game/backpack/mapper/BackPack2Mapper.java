package com.game.backpack.mapper;

import com.game.backpack.bean.GoodsEntity;

import java.util.List;

public interface BackPack2Mapper {
    /**
     * 根据角色id找goods
     * @param roleId 角色id
     * @return
     */
    public GoodsEntity getGoodsEntityByRoleId(int roleId);

    /**
     * 查询所有物品
     * @return
     */
    public List<GoodsEntity> getGoodsEntity();

    /**
     * 保存物品信息
     */
    public void saveGoodsInfo(GoodsEntity goodsEntity);
}
