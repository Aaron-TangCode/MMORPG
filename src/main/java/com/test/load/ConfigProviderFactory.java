package com.test.load;

import org.w3c.dom.Document;

import java.util.Properties;

/**
 *
 *
 * @author Bean
 * @date 2016年1月21日 上午11:56:28
 * @version 1.0
 *
 */
public class ConfigProviderFactory {

    private ConfigProviderFactory() {
        throw new UnsupportedOperationException("Unable to initialize a factory class : "
                + getClass().getSimpleName());
    }

//    public static IConfigProvider<Document> createDocumentProvider(String filePath) {
//        return new DocumentProvider(filePath);
//    }
//
//    public static IConfigProvider<Properties> createPropertiesProvider(String filePath) {
//        return new PropertiesProvider(filePath);
//    }
//
//    public static IConfigProvider<Digester> createDigesterProvider(String filePath) {
//            return new DigesterProvider(filePath);
//    }
}