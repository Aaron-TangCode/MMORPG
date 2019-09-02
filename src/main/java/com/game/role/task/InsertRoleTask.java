package com.game.role.task;

import com.game.role.bean.ConcreteRole;
import com.game.role.mapper.RoleMapper;
import com.game.utils.SqlUtils;
import org.apache.ibatis.session.SqlSession;

import java.util.concurrent.Callable;

/**
 * @ClassName InsertRoleTask
 * @Description 添加role任务
 * @Author DELL
 * @Date 2019/8/19 21:32
 * @Version 1.0
 */
public class InsertRoleTask implements Callable {
    /**
     * 角色
     */
    private ConcreteRole role;

    public InsertRoleTask(ConcreteRole role) {
        this.role = role;
    }

    /**
     * 执行任务
     * @return Object
     * @throws Exception 异常
     */
    @Override
    public Object call() throws Exception {
        SqlSession session = SqlUtils.getSession();
        try {
            RoleMapper mapper = session.getMapper(RoleMapper.class);
            mapper.insertRole(role);
            session.commit();
        }finally {
            session.close();
        }
        return null;
    }
}
