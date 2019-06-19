package com.game.backpack.service;

import com.game.backpack.bean.Goods;
import com.game.backpack.repository.BackpackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName BackpackService
 * @Description 背包逻辑层
 * @Author DELL
 * @Date 2019/6/17 15:36
 * @Version 1.0
 */
@Service
public class BackpackService {
    @Autowired
    private BackpackRepository backpackRepository;

    public List<Goods> getGoodsByRoleId(int roleId) {
        return backpackRepository.getGoodsByRoleId(roleId);
    }

    public void insertGoods(Goods newGoods) {
        backpackRepository.insertGoods(newGoods);
    }

    public void updateGoodsByRoleId(int roleId,int goodsId) {
        backpackRepository.updateGoodsByRoleId(roleId,goodsId);
    }

    public void updateGoodsByRoleIdDel(int roleId, Integer goodsId) {
        backpackRepository.updateGoodsByRoleIdDel(roleId,goodsId);
    }
}
