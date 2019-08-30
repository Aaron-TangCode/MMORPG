package com.game.backpack.repository;

import com.game.backpack.bean.Goods;
import com.game.backpack.mapper.BackpackMapper;
import com.game.backpack.task.BackpackUpdateTask;
import com.game.utils.SqlUtils;
import com.game.utils.ThreadPoolUtils;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName BackpackRepository
 * @Description 背包repository
 * @Author DELL
 * @Date 2019/6/17 15:36
 * @Version 1.0
 */
@Repository
public class BackpackRepository {
    /**
     * 通过角色id获取物品
     * @param roleId 角色id
     * @return
     */
    public List<Goods> getGoodsByRoleId(int roleId) {
        SqlSession session = SqlUtils.getSession();
        try{
            BackpackMapper mapper = session.getMapper(BackpackMapper.class);
            List<Goods> goods = mapper.getGoodsByRoleId(roleId);
            return goods;
        }finally {
            session.close();
        }
    }

    /**
     * 增加物品
     * @param goods 物品
     */
    public void insertGoods(Goods goods) {
//        BackpackInsertTask task = new BackpackInsertTask(goods);
//        ThreadPoolUtils.getThreadPool().execute(task);
        SqlSession session = SqlUtils.getSession();
        try{
            BackpackMapper mapper = session.getMapper(BackpackMapper.class);
            mapper.insertGoods(goods);
            session.commit();
        }finally {
            session.close();
        }
    }

    /**
     * 更新物品数量(加)
     * @param roleId 角色id
     * @param goodsId 物品id
     */
    public void updateGoodsByRoleId(int roleId,int goodsId) {
        BackpackUpdateTask task = new BackpackUpdateTask(roleId,goodsId);
        ThreadPoolUtils.getThreadPool().execute(task);
    }

    /**
     * 获取角色拥有的物品数量
     * @param roleId 角色id
     * @return 返回整数
     */
    public int getExistedGoodsCountsByRoleId(int roleId) {
        SqlSession session = SqlUtils.getSession();
        try{
            BackpackMapper mapper = session.getMapper(BackpackMapper.class);
            int counts = mapper.getExistedGoodsCountsByRoleId(roleId);
            return counts;
        }finally {
            session.close();
        }
    }

    /**
     * 更新物品数量（减）
     * @param roleId 角色id
     * @param goodsId 物品id
     */
    public void updateGoodsByRoleIdDel(int roleId, Integer goodsId) {
        SqlSession session = SqlUtils.getSession();
        try{
            BackpackMapper mapper = session.getMapper(BackpackMapper.class);
            mapper.updateGoodsByRoleIdDel(roleId,goodsId);
            session.commit();
        }finally {
            session.close();
        }
//        BackpackUpdateDelTask task = new BackpackUpdateDelTask(roleId,goodsId);
//        UserThreadPool.ACCOUNT_SERVICE[0].submit(task);
    }

    /**
     * 通过物品id获取物品
     * @param goodsId 物品id
     * @return 返回物品
     */
    public Goods getGoodsById(String goodsId) {
        SqlSession session = SqlUtils.getSession();
        try{
            BackpackMapper mapper = session.getMapper(BackpackMapper.class);
          return mapper.getGoodsById(Integer.parseInt(goodsId));
        }finally {
            session.close();
        }
    }
}
