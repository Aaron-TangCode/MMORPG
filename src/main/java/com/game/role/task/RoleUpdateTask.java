package com.game.role.task;

import com.game.role.mapper.RoleMapper;
import com.game.utils.SqlUtils;
import org.apache.ibatis.session.SqlSession;

/**
 * @ClassName RoleUpdateTask
 * @Description 角色更新任务类
 * @Author DELL
 * @Date 2019/6/6 16:13
 * @Version 1.0
 */
public class RoleUpdateTask implements Runnable {
    /**
     * 角色名
     */
    private String roleName;
    /**
     * 目的地
     */
    private Integer dest;

    public RoleUpdateTask(String roleName, Integer dest) {
        this.roleName = roleName;
        this.dest = dest;
    }

    @Override
    public void run() {
        SqlSession session = SqlUtils.getSession();
        try {
            RoleMapper mapper = session.getMapper(RoleMapper.class);
            boolean isSuccess = mapper.updateMap(roleName,dest);
            session.commit();
        }finally {
            session.close();
        }
    }
}
