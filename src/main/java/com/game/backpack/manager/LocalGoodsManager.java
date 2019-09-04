package com.game.backpack.manager;

import com.game.backpack.bean.GoodsBag;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName LocalGoodsManager
 * @Description 物品的缓存管理器
 * @Author DELL
 * @Date 2019/9/4 20:56
 * @Version 1.0
 */
public class LocalGoodsManager {
    private static volatile Map<Integer, List<GoodsBag>> localGoodsMap = new HashMap<>();

    private LocalGoodsManager(){}

    public static Map<Integer, List<GoodsBag>> getLocalGoodsMap(){
        return localGoodsMap;
    }
}
