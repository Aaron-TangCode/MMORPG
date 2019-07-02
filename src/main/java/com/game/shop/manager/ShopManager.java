package com.game.shop.manager;

import com.game.annotation.ExcelAnnotation;
import com.game.backpack.bean.Goods;
import com.game.utils.MapUtils;
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
    private static volatile Map<String,Goods> shopMap = null;
    public static Map<String,Goods> getShopMap(){
        if(shopMap==null){
            synchronized (ShopManager.class){
                if (shopMap==null){
                    shopMap = new HashMap<>();
                }
            }
        }
        return shopMap;
    }
    @ExcelAnnotation
    public void init(){
        Map<String,Goods> map = getShopMap();
        Set<Map.Entry<String, Goods>> entrySet = MapUtils.getGoodsMap().entrySet();
        Iterator<Map.Entry<String, Goods>> iterator = entrySet.iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Goods> next = iterator.next();
            map.put(next.getKey(),next.getValue());
        }
    }
}
