package com.game.backpack.repository;

import com.game.backpack.bean.Goods;
import com.game.backpack.mapper.BackpackMapper;
import com.game.utils.SqlUtils;
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
     * @param newGoods
     */
    public void insertGoods(Goods newGoods) {
        SqlSession session = SqlUtils.getSession();
        try{
            BackpackMapper mapper = session.getMapper(BackpackMapper.class);
            mapper.insertGoods(newGoods);
            session.commit();
        }finally {
            session.close();
        }
    }

    /**
     * 更新物品数量
     * @param roleId
     */
    public void updateGoodsByRoleId(int roleId,int goodsId) {
        SqlSession session = SqlUtils.getSession();
        try{
            BackpackMapper mapper = session.getMapper(BackpackMapper.class);
           mapper.updateGoodsByRoleId(roleId,goodsId);
           session.commit();
        }finally {
            session.close();
        }
    }

    /**
     * 获取角色拥有的物品数量
     * @param roleId
     * @return
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
}
