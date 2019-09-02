package com.game.gang.task;

import com.game.gang.bean.GangEntity;
import com.game.gang.mapper.GangEntityMapper;
import com.game.utils.SqlUtils;
import org.apache.ibatis.session.SqlSession;

import java.util.concurrent.Callable;

/**
 * @ClassName UpdateGangEntityTask
 * @Description 更新工会成员任务器
 * @Author DELL
 * @Date 2019/8/19 20:14
 * @Version 1.0
 */
public class UpdateGangEntityTask implements Callable {
    /**
     * 工会
     */
    private GangEntity gangEntity;

    public UpdateGangEntityTask(GangEntity gangEntity) {
        this.gangEntity = gangEntity;
    }

    @Override
    public Object call() throws Exception {
        SqlSession session = SqlUtils.getSession();
        try {
            GangEntityMapper mapper = session.getMapper(GangEntityMapper.class);
            mapper.updateGangEntity(gangEntity);
            session.commit();
        }finally {
            session.close();
        }
        return null;
    }
}
