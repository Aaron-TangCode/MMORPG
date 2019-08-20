package com.game.trade.manager;

import com.game.trade.bean.Trade;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName TradeMap
 * @Description 交易map
 * @Author DELL
 * @Date 2019/7/15 16:58
 * @Version 1.0
 */
public class TradeMap {
    private static volatile Map<String, Trade> tradeMap = null;

    private TradeMap(){}

    public static Map<String,Trade> getTradeMap(){
        if(tradeMap==null){
            synchronized (TradeMap.class){
                if(tradeMap==null){
                    tradeMap = new HashMap<>();
                }
            }
        }
        return tradeMap;
    }
}
