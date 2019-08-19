package com.game.backpack.task;

import com.game.backpack.bean.Goods;
import com.game.backpack.mapper.BackpackMapper;
import com.game.utils.SqlUtils;
import org.apache.ibatis.session.SqlSession;

/**
 * @ClassName BackpackInsertTask
 * @Description 添加物品任务
 * @Author DELL
 * @Date 2019/6/19 10:57
 * @Version 1.0
 */
public class BackpackInsertTask implements Runnable {
    private Goods goods;
    public BackpackInsertTask(Goods goods){
        this.goods = goods;
    }
    @Override
    public void run() {
        SqlSession session = SqlUtils.getSession();
        try{
            BackpackMapper mapper = session.getMapper(BackpackMapper.class);
            mapper.insertGoods(goods);
            session.commit();
        }finally {
            session.close();
        }
    }
}
