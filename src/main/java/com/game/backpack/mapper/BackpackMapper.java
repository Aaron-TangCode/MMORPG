package com.game.backpack.mapper;

import com.game.backpack.bean.Goods;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @ClassName BackpackMapper
 * @Description 背包映射类
 * @Author DELL
 * @Date 2019/6/17 15:43
 * @Version 1.0
 */
public interface BackpackMapper {
    /**
     * 通过角色id来找物品
     * @param roleId
     * @return
     */
    public List<Goods> getGoodsByRoleId(int roleId);

    /**
     * 增加物品
     * @param newGoods
     */
    public void insertGoods(Goods newGoods);

    /**
     * 更新物品（加）
     * @param roleId
     */
    public void updateGoodsByRoleId(@Param("roleId") int roleId,@Param("goodsId") int goodsId);

    /**
     * 查询角色拥有的物品数量
     * @param roleId
     */
    public int getExistedGoodsCountsByRoleId(int roleId);

    /**
     * 更新物品数量（减）
     * @param roleId
     * @param goodsId
     */
    public void updateGoodsByRoleIdDel(@Param("roleId") int roleId,@Param("goodsId") int goodsId);

}
