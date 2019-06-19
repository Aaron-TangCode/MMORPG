package com.game.backpack.task;

import com.game.backpack.bean.Goods;
import com.game.backpack.mapper.BackpackMapper;
import com.game.utils.SqlUtils;
import org.apache.ibatis.session.SqlSession;

/**
 * @ClassName BackpackInserTask
 * @Description TODO
 * @Author DELL
 * @Date 2019/6/19 10:57
 * @Version 1.0
 */
public class BackpackInserTask implements Runnable {
    private Goods goods;
    public BackpackInserTask(Goods goods){
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
