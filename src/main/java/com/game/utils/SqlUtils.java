package com.game.utils;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

/**
 * @ClassName SqlUtils
 * @Description 数据库工具类
 * @Author DELL
 * @Date 2019/5/2717:18
 * @Version 1.0
 */
@Component
public class SqlUtils {
    /**
     * 获取session
     */
    public static SqlSession getSession(){
        String resource = "config/mybatis-config.xml";
        InputStream inputStream = null;
        try {
            inputStream = Resources.getResourceAsStream(resource);
        } catch (IOException e) {
            e.printStackTrace();
        }
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        //获取session
        SqlSession session = sqlSessionFactory.openSession();
        //返回session
        return session;
    }


}
