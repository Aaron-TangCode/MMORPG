package com.game.backpack.task;

import com.alibaba.fastjson.JSONArray;
import com.game.backpack.bean.GoodsBag;
import com.game.backpack.bean.GoodsEntity;
import com.game.backpack.manager.LocalGoodsManager;
import com.game.backpack.service.BackpackService;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @ClassName UpdateMapMsgTask
 * @Description 定时器更新数据库
 * @Author DELL
 * @Date 2019/8/26 22:11
 * @Version 1.0
 */
public class UpdateMapMsgTask implements Runnable {

    private BackpackService backpackService;

    public UpdateMapMsgTask(BackpackService backpackService) {
        this.backpackService = backpackService;
    }

    @Override
    public void run() {
        //获取缓存
        Map<Integer, List<GoodsBag>> localGoodsMap = LocalGoodsManager.getLocalGoodsMap();
        //遍历更新
        Set<Map.Entry<Integer, List<GoodsBag>>> entrySet = localGoodsMap.entrySet();
        //bag
        GoodsEntity goodsEntity = new GoodsEntity();
        for (Map.Entry<Integer, List<GoodsBag>> entry : entrySet) {
            //roleid
            Integer roleId = entry.getKey();
            List<GoodsBag> goodsBagList = entry.getValue();
            //goodsInfo
            String goodsInfo = JSONArray.toJSONString(goodsBagList);
            //设置值
            goodsEntity.setGoodsBag(goodsInfo);
            goodsEntity.setRoleId(String.valueOf(roleId));
            //保存数据
            backpackService.saveGoodsInfo(goodsEntity);
        }
    }
}
