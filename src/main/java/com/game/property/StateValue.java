package com.game.property;

/**
 * @ClassName StateValue
 * @Description 属性分类值
 * @Author DELL
 * @Date 2019/6/20 21:24
 * @Version 1.0
 */
public class StateValue {
    private int basic;
    private int total;
    private int additional;
    private int ratio;

    public int getBasic() {
        return basic;
    }

    public void setBasic(int basic) {
        this.basic = basic;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = getBasic()+getAdditional();
    }

    public int getAdditional() {
        return additional;
    }

    public void setAdditional(int additional) {
        this.additional = additional;
    }

    public int getRatio() {
        return ratio;
    }

    public void setRatio(int ratio) {
        this.ratio = ratio;
    }
}
