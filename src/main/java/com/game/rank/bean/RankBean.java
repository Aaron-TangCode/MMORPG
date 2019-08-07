package com.game.rank.bean;

import com.game.role.bean.ConcreteRole;

/**
 * @ClassName RankBean
 * @Description TODO
 * @Author DELL
 * @Date 2019/8/5 16:16
 * @Version 1.0
 */
public class RankBean {
    private ConcreteRole role;
    private Integer comat;

    public ConcreteRole getRole() {
        return role;
    }

    public void setRole(ConcreteRole role) {
        this.role = role;
    }

    public Integer getComat() {
        return comat;
    }

    public void setComat(Integer comat) {
        this.comat = comat;
    }
}
