package com.game.backpack.bean;

/**
 * @ClassName GoodsBag
 * @Description 小背包
 * @Author DELL
 * @Date 2019/9/4 12:29
 * @Version 1.0
 */
public class GoodsBag {
    /**
     * 商品id
     */
    private String goodsId;
    /**
     * 商品数量
     */
    private String count;

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }
}
