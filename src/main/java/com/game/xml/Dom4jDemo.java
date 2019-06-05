package com.game.xml;

import com.game.map.Map_Mapping;
import com.game.utils.MapUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;
@XmlAnnotation
@Component
public class Dom4jDemo {
    private static final String PATH = "src/main/resources/xmldata/employees.xml";
    @XmlAnnotation
    public static void parserXml() {
        File inputXml = new File(PATH);
        SAXReader saxReader = new SAXReader();
        Map_Mapping mapMapping = null;
        try {
            Document document = saxReader.read(inputXml);
            Element employees = document.getRootElement();
            for (Iterator i = employees.elementIterator(); i.hasNext(); ) {
                Element employee = (Element) i.next();
                for (Iterator j = employee.elementIterator(); j.hasNext(); ) {
                    Element node = (Element) j.next();
                    System.out.println(node.getName() + ":" + node.getText());
                    mapMapping = new Map_Mapping();
                    mapMapping.setId(Integer.parseInt(node.getText()));
                    node = (Element) j.next();
                    mapMapping.setSrc_map(Integer.parseInt(node.getText()));
                    node = (Element) j.next();
                    mapMapping.setDest_map(Integer.parseInt(node.getText()));

                }
                MapUtils.getListRole().add(mapMapping);
            }
            System.out.println(MapUtils.getListRole().size());
        } catch (DocumentException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("dom4j parserXml牛逼");
    }


} 