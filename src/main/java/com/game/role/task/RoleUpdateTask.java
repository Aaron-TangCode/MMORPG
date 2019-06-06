package com.game.role.task;

import com.game.mapper.RoleMapper;
import com.game.utils.SqlUtils;
import org.apache.ibatis.session.SqlSession;

/**
 * @ClassName RoleUpdateTask
 * @Description TODO
 * @Author DELL
 * @Date 2019/6/6 16:13
 * @Version 1.0
 */
public class RoleUpdateTask implements Runnable {
    private String roleName;
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
