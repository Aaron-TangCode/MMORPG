package com.game.auction.bean;

/**
 * @ClassName Auction
 * @Description 拍卖行实体类
 * @Author DELL
 * @Date 2019/7/18 10:59
 * @Version 1.0
 */
public class Auction {
    /**
     * 唯一id
     */
    private Integer id;
    /**
     * 物品名称
     */
    private String goodsName;
    /**
     * 物品价格
     */
    private Integer price;
    /**
     * 卖家
     */
    private String seller;
    /**
     * 物品数量
     */
    private Integer number;
    /**
     * 物品剩余的拍卖时间
     */
    private Integer leftTime;
    /**
     * 买家
     */
    private String buyer;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getLeftTime() {
        return leftTime;
    }

    public void setLeftTime(Integer leftTime) {
        this.leftTime = leftTime;
    }

    public String getBuyer() {
        return buyer;
    }

    public void setBuyer(String buyer) {
        this.buyer = buyer;
    }
}
