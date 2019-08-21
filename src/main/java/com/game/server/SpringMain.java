package com.game.server;

import com.game.annotation.ExcelAnnotation;
import com.game.dispatcher.ClassUtil;
import com.game.dispatcher.MyAnnotationUtil;
import com.game.dispatcher.StringUtil;
import com.game.event.annotation.EventAnnotation;
import com.game.event.core.IEvent;
import com.game.event.handler.IHandler;
import com.game.event.manager.EventMap;
import com.game.server.local.LocalMessageMap;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
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
     */
        static {
            applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");

            //解析资源文件得到资源对象
            Properties prop = MyAnnotationUtil.parsePackAgeByProperties("src/main/resources/my.properties");
            //得到基础控制器包
            String basePackage=prop.getProperty("excel.file");
            //解析失败
            if (!StringUtil.isNotNullOrEmpty(basePackage)) {
                throw new RuntimeException("解析properties资源文件失败,请检查是否是properties文件路径有错或资源文件中属性名是否是my.handler.package");
            }
            //拿到基础包之后,去得到所有Controller类
            List<Class> clzes = ClassUtil.parseAllController(basePackage);
            //迭代所有全限定名
            for (Class clz : clzes) {
                //判断是否有自定义注解
                if(clz.isAnnotationPresent(ExcelAnnotation.class)) {
                    try {
                        //獲取對象
                        Object obj = applicationContext.getBean(clz);
                        //獲取所有方法
                        Method[] methods = clz.getMethods();
                        for (Method method:methods){
                            if(method.isAnnotationPresent(ExcelAnnotation.class)) {
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

                if(clz.isAnnotationPresent(EventAnnotation.class)){
                    try {
                        Object obj = applicationContext.getBean(clz);
                        Method[] methods = clz.getMethods();
                        for (Method method:methods){
                            if(method.isAnnotationPresent(EventAnnotation.class)){
                                //获取参数
                                Class<? extends IEvent>[] parameterTypes = (Class<? extends IEvent>[]) method.getParameterTypes();
                                List<IHandler> handlerList = EventMap.getEventMap().get(parameterTypes);
                                if(handlerList==null){
                                    handlerList = new ArrayList<>();
                                }
                                if(obj instanceof IHandler){
                                    IHandler handler = (IHandler)obj;
                                    handlerList.add(handler);
                                    //添加到容器
                                    EventMap.getEventMap().put(parameterTypes[0],handlerList);
                                }
                            }
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        LocalMessageMap.readAllMessageType();

        }
    public static void main(String[] args) {
        Server server = applicationContext.getBean(Server.class);
        //启动服务端
        server.start(8899);

    }

}