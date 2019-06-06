package com.game.server;

import com.game.dispatcher.*;
import com.game.xml.XmlAnnotation;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Properties;

/**
 * @ClassName SpringMain
 * @Description 服务端启动入口
 * @Author DELL
 * @Date 2019/5/2812:36
 * @Version 1.0
 */
@Component
public class SpringMain {
    public static ApplicationContext applicationContext;

    /**
     * 服务端启动前加载静态数据
     * 来自Mac的问候123123123123123123
     * 来自windows的问候
     */
        static {
            applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
            //解析资源文件得到资源对象
            Properties prop = MyAnnotationUtil.parsePackAgeByProperties("src/main/resources/my.properties");
            //得到基础控制器包
            String basePackage=prop.getProperty("xml.file");
            //解析失败
            if (!StringUtil.isNotNullOrEmpty(basePackage)) {
                throw new RuntimeException("解析properties资源文件失败,请检查是否是properties文件路径有错或资源文件中属性名是否是my.controller.package");
            }
            //拿到基础包之后,去得到所有Controller类
            List<Class> clzes = ClassUtil.parseAllController(basePackage);
            //迭代所有全限定名
            for (Class clz : clzes) {
                //判断是否有自定义注解
                if(clz.isAnnotationPresent(XmlAnnotation.class)) {
                    try {
                        //獲取對象
                        Object obj = applicationContext.getBean(clz);
                        //獲取所有方法
                        Method[] methods = clz.getMethods();
                        for (Method method:methods){
                            if(method.isAnnotationPresent(XmlAnnotation.class)) {
                                //回調方法
                                method.invoke(obj);
                            }
                        }
                    }  catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }

                }
            }
        }
    public static void main(String[] args) {
        Server server = applicationContext.getBean(Server.class);
        //启动服务端
        server.start(8899);
    }

}