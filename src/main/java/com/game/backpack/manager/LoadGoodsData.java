package com.game.backpack.manager;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.game.backpack.bean.GoodsBag;
import com.game.backpack.bean.GoodsEntity;
import com.game.backpack.repository.BackpackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @ClassName LoadGoodsData
 * @Description 加载物品数据到缓存
 * @Author DELL
 * @Date 2019/9/4 21:00
 * @Version 1.0
 */
@Component
public class LoadGoodsData {
    @Autowired
    private BackpackRepository backpackRepository;
    public void loadingGoodsData(Integer roleId){
        //查询某一个角色的所有物品
        GoodsEntity goodsEntity = backpackRepository.getGoodsEntityByRoleId(roleId);
        if(goodsEntity==null){
            return;
        }
        //获取缓存容器map
        Map<Integer, List<GoodsBag>> localGoodsMap = LocalGoodsManager.getLocalGoodsMap();
        //获取goodsBag
        String goodsBag = goodsEntity.getGoodsBag();
        //json数组转对象
        JSONArray jsonArray = JSON.parseArray(goodsBag);
        //List
        List<GoodsBag> goodsBagList = new ArrayList<>();
        for (Object obj : jsonArray) {
            GoodsBag bag = JSONObject.parseObject(obj.toString(), GoodsBag.class);
            goodsBagList.add(bag);
        }
        //存储
        localGoodsMap.put(roleId,goodsBagList);
    }
}
