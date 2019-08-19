package com.game.gang.task;

import com.game.gang.bean.GangMemberEntity;
import com.game.gang.mapper.GangMemberEntityMapper;
import com.game.utils.SqlUtils;
import org.apache.ibatis.session.SqlSession;

import java.util.concurrent.Callable;

/**
 * @ClassName InsertGangMemberTask
 * @Description TODO
 * @Author DELL
 * @Date 2019/8/19 20:12
 * @Version 1.0
 */
public class InsertGangMemberTask implements Callable {
    private GangMemberEntity entity;

    public InsertGangMemberTask(GangMemberEntity entity) {
        this.entity = entity;
    }

    @Override
    public Object call() throws Exception {
        SqlSession session = SqlUtils.getSession();
        try {
            GangMemberEntityMapper mapper = session.getMapper(GangMemberEntityMapper.class);
            mapper.insertGangMember(entity);
            session.commit();
        }finally {
            session.close();
        }
        return null;
    }
}
