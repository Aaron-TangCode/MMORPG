package com.test.load.demo02;

import com.game.data.MapMapping;
import com.game.utils.MapUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;

public class Dom4jDemo{
    private static final String PATH = "src/main/resources/xmldata/";
    public static void createXml(String fileName) {
        Document document = DocumentHelper.createDocument();
        Element employees = document.addElement("employees");
        Element employee = employees.addElement("employee");
        Element name = employee.addElement("name");
        name.setText("活这么大就没饱过");
        Element sex = employee.addElement("sex");
        sex.setText("m");
        Element age = employee.addElement("age");
        age.setText("24");
        try {
            Writer fileWriter = new FileWriter(PATH+fileName);
            XMLWriter xmlWriter = new XMLWriter(fileWriter);
            xmlWriter.write(document);
            xmlWriter.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void parserXml(String fileName) {
        File inputXml = new File(PATH+fileName);
        SAXReader saxReader = new SAXReader();
        MapMapping mapMapping = null;
        try {
            Document document = saxReader.read(inputXml);
            Element employees = document.getRootElement();
            for (Iterator i = employees.elementIterator(); i.hasNext(); ) {
                Element employee = (Element) i.next();
                for (Iterator j = employee.elementIterator(); j.hasNext(); ) {
                    Element node = (Element) j.next();
                    System.out.println(node.getName() + ":" + node.getText());
                    mapMapping = new MapMapping();
                    mapMapping.setId(Integer.parseInt(node.getText()));
                    node = (Element) j.next();
                    mapMapping.setSrcMap(Integer.parseInt(node.getText()));
                    node = (Element) j.next();
                    mapMapping.setDestMap(Integer.parseInt(node.getText()));

                }
                MapUtils.getListRole().add(mapMapping);
            }
            System.out.println(MapUtils.getListRole().size());
        } catch (DocumentException e) {
            System.out.println(e.getMessage());
        }
//        System.out.println("dom4j parserXml");
    }

    public static void main(String[] args) {
//        createXml("mapMapping.xml");
        parserXml("mapMapping.xml");
        Iterator<MapMapping> iterator = MapUtils.getListRole().iterator();
        while(iterator.hasNext()){
            MapMapping next = iterator.next();
            System.out.println(next.getId()+":"+next.getSrcMap()+":"+next.getDestMap());
        }
    }
} 