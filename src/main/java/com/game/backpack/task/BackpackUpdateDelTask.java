package com.game.backpack.task;

import com.game.backpack.mapper.BackpackMapper;
import com.game.utils.SqlUtils;
import org.apache.ibatis.session.SqlSession;

/**
 * @ClassName BackpackUpdateDelTask
 * @Description 背包更新任务
 * @Author DELL
 * @Date 2019/6/19 10:51
 * @Version 1.0
 */
public class BackpackUpdateDelTask implements Runnable {
    /**
     * 角色id
     */
    private int roleId;
    /**
     * 物品id
     */
    private Integer goodsId;

    public BackpackUpdateDelTask(int roleId, Integer goodsId) {
        this.roleId = roleId;
        this.goodsId = goodsId;
    }

    @Override
    public void run() {
        SqlSession session = SqlUtils.getSession();
        try{
            BackpackMapper mapper = session.getMapper(BackpackMapper.class);
            mapper.updateGoodsByRoleIdDel(roleId,goodsId);
            session.commit();
        }finally {
            session.close();
        }
    }
}
