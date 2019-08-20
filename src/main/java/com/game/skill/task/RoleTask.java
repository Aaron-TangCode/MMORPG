package com.game.skill.task;

import com.game.role.bean.ConcreteRole;
import com.game.role.mapper.RoleMapper;
import com.game.utils.SqlUtils;
import org.apache.ibatis.session.SqlSession;

/**
 * @ClassName RoleTask
 * @Description 角色任务
 * @Author DELL
 * @Date 2019/6/13 16:20
 * @Version 1.0
 */
public class RoleTask implements Runnable {
    private ConcreteRole concreteRole;

    public RoleTask(ConcreteRole concreteRole){
        this.concreteRole = concreteRole;
    }
    @Override
    public void run() {
        SqlSession session = SqlUtils.getSession();
        try {
            RoleMapper mapper = session.getMapper(RoleMapper.class);
            mapper.updateRole(concreteRole);
            session.commit();
        }finally {
            session.close();
        }
    }
}
