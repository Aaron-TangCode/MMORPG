package com.game.shop.manager;

import com.game.annotation.ExcelAnnotation;
import com.game.backpack.bean.GoodsResource;
import com.game.utils.CacheUtils;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * @ClassName ShopManager
 * @Description 商店系统的缓存
 * @Author DELL
 * @Date 2019/7/1 20:51
 * @Version 1.0
 */
@Component
@ExcelAnnotation
public class ShopManager {
    /**
     * 商店缓存map
     */
    private static volatile Map<String, GoodsResource> shopMap = null;
    public static Map<String, GoodsResource> getShopMap(){
        if(shopMap==null){
            synchronized (ShopManager.class){
                if (shopMap==null){
                    shopMap = new HashMap<>();
                }
            }
        }
        return shopMap;
    }

    /**
     * 初始化商店缓存
     */
    @ExcelAnnotation
    public void init(){
        Map<String, GoodsResource> map = getShopMap();
        Set<Map.Entry<String, GoodsResource>> entrySet = CacheUtils.getGoodsMap().entrySet();
        Iterator<Map.Entry<String, GoodsResource>> iterator = entrySet.iterator();
        //遍历注入值
        while (iterator.hasNext()) {
            Map.Entry<String, GoodsResource> next = iterator.next();
            map.put(next.getKey(),next.getValue());
        }
    }
}
