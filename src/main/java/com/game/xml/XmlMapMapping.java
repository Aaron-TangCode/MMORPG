package com.game.xml;

import com.game.excel.bean.MapMapping;
import com.game.utils.MapUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Iterator;
@XmlAnnotation
@Component
public class XmlMapMapping {
    private static final String PATH = "src/main/resources/xmldata/mapMapping.xml";
    @XmlAnnotation
    public static void parserXml() {
        File inputXml = new File(PATH);
        SAXReader saxReader = new SAXReader();
        MapMapping mapMapping = null;
        try {
            Document document = saxReader.read(inputXml);
            Element employees = document.getRootElement();
            for (Iterator i = employees.elementIterator(); i.hasNext(); ) {
                Element employee = (Element) i.next();
                for (Iterator j = employee.elementIterator(); j.hasNext(); ) {
                    Element node = (Element) j.next();
                    mapMapping = new MapMapping();
                    mapMapping.setId(Integer.parseInt(node.getText()));
                    node = (Element) j.next();
                    mapMapping.setSrcMap(Integer.parseInt(node.getText()));
                    node = (Element) j.next();
                    mapMapping.setDestMap(Integer.parseInt(node.getText()));

                }
                MapUtils.getListRole().add(mapMapping);
            }
        } catch (DocumentException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("静态数据--地图映射加载完毕");
    }


} 