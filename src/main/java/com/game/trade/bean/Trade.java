package com.game.trade.bean;

import com.game.role.bean.ConcreteRole;

/**
 * @ClassName Trade
 * @Description TODO
 * @Author DELL
 * @Date 2019/7/15 16:40
 * @Version 1.0
 */
public class Trade {
    private String uuid;
    private ConcreteRole from;
    private ConcreteRole to;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public ConcreteRole getFrom() {
        return from;
    }

    public void setFrom(ConcreteRole from) {
        this.from = from;
    }

    public ConcreteRole getTo() {
        return to;
    }

    public void setTo(ConcreteRole to) {
        this.to = to;
    }

    public Trade(String uuid, ConcreteRole from, ConcreteRole to) {
        this.uuid = uuid;
        this.from = from;
        this.to = to;
    }
}
