package com.test;

import com.game.data.MapMapping;
import com.game.map.bean.ConcreteMap;
import com.game.mapper.MapMapper;
import com.game.mapper.RoleMapper;
import com.game.mapper.UserMapper;
import com.game.role.bean.ConcreteRole;
import com.game.user.bean.User;
import com.game.utils.MapUtils;
import com.game.utils.SqlUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;


/**
 * @ClassName Main
 * @Description TODO
 * @Author DELL
 * @Date 2019/5/2716:46
 * @Version 1.0
 */
public class Main {
    @Test
    public void testSelect() throws IOException {
        SqlSession session = SqlUtils.getSession();
        try {
            UserMapper mapper = session.getMapper(UserMapper.class);
            User user = mapper.selectUserById(2);
            System.out.println(user.getUsername());
        } finally {
            session.close();
        }
    }
    @Test
    public void testInsert() throws IOException {
        SqlSession session = SqlUtils.getSession();
        try {
            UserMapper mapper = session.getMapper(UserMapper.class);
            User user = new User();
            user.setPassword("123456");
            user.setUsername("987654");
            boolean isSuccess = mapper.addUser(user);
            System.out.println(isSuccess);
            session.commit();
        } finally {
            session.close();
        }
    }

    @Test
    public void test03() throws IOException {
        SqlSession session = SqlUtils.getSession();
        try {
            UserMapper mapper = session.getMapper(UserMapper.class);
            int id = mapper.getUserRoleIdByUsername("123");
            System.out.println(id);
        } finally {
            session.close();
        }
    }
    @Test
    public void test04() throws IOException {
        SqlSession session = SqlUtils.getSession();
        try {
            RoleMapper mapper = session.getMapper(RoleMapper.class);
            ConcreteRole role = mapper.getRoleById(2);
            System.out.println(role);
        } finally {
            session.close();
        }
    }
    @Test
    public void test05() throws IOException {
        SqlSession session = SqlUtils.getSession();
        try {
            MapMapper mapper = session.getMapper(MapMapper.class);
            ConcreteMap map = mapper.getMapById(1);
            System.out.println(map);
        } finally {
            session.close();
        }
    }



    public static void main(String[] args) {
        System.out.println(System.getProperty("java.class.path"));//系统的classpaht路径
        System.out.println(System.getProperty("user.dir"));//用户的当前路径
    }
}
