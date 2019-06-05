package com.test.load.demo01;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;


import java.io.File;

/**
 * @ClassName TestMain
 * @Description TODO
 * @Author DELL
 * @Date 2019/6/415:41
 * @Version 1.0
 */
public class TestMain {
    public static void main(String[] args) {
        ICarFactory icf = null;
        try{
            File file=new File("src/main/resources/load/factory.xml");
            SAXReader reader=new SAXReader();
            Document doc=reader.read(file);
            Element root=doc.getRootElement();
            String className=root.elementText("Factroy-Name");
            //通过className得到相应的类和类实例,并将其赋给icf；必须确保className
            // 对应的类有一个无参构造方法
            icf=(ICarFactory)Class.forName(className).newInstance();
            //制造相应的汽车
            icf.makeCar();
        } catch (InstantiationException e) {
            System.out.println("实例化失败");
        } catch (IllegalAccessException e) {
            System.out.println("非法访问");
        } catch (ClassNotFoundException e) {
            System.out.println("找不到该类");
        } catch (DocumentException e) {
            System.out.println("找不到文件");
        } finally {

        }
    }
}
