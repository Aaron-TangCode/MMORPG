package com.game.gang.task;

import com.game.gang.mapper.GangEntityMapper;
import com.game.utils.SqlUtils;
import org.apache.ibatis.session.SqlSession;

import java.util.concurrent.Callable;

/**
 * @ClassName InsertGangTask
 * @Description 添加工会任务器
 * @Author DELL
 * @Date 2019/8/19 20:10
 * @Version 1.0
 */
public class InsertGangTask implements Callable {
    /**
     * 工会名字
     */
    private String gangName;

    public InsertGangTask(String gangName) {
        this.gangName = gangName;
    }

    @Override
    public Object call() throws Exception {
        SqlSession session = SqlUtils.getSession();
        try {
            GangEntityMapper mapper = session.getMapper(GangEntityMapper.class);
            mapper.insertGang(gangName);
            session.commit();
        }finally {
            session.close();
        }
        return null;
    }
}
