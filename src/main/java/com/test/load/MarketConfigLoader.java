package com.test.load;

import org.w3c.dom.Document;

/**
 *
 *
 * @author Bean
 * @date 2016年1月21日 下午11:18:56
 * @version 1.0
 *
 */
public class MarketConfigLoader extends AbstractConfigLoader<MarketConfig, Document> {

    protected MarketConfigLoader(IConfigProvider<Document> provider) {
        super(provider);
    }

    @Override
    public MarketConfig load(Document document) throws Exception {
        
        MarketConfig marketConfig = new MarketConfig();
        AppleConfig appleConfig = new AppleConfig();
        EggConfig eggConfig = new EggConfig();
        /**
         * 在这里处理document,然后就能得到
         * AppleConfig和EggConfg
         * 此处代码省略
         */
        marketConfig.setAppleConfig(appleConfig);
        marketConfig.setEggConfig(eggConfig);
        
        /**
         * 由于OwnerConfig是需要properties方式来加载，不是xml
         * 所以这里要新建一个OwnerConfigLoader，委托它来加载OwnerConfig
         */
        
       // OwnerConfigLoader ownerConfigLoader = new OwnerConfigLoader(ConfigProviderFactory.createPropertiesProvider("YOUR_FILE_PATH"));
//        OwnerConfig ownerConfig = ownerConfigLoader.load();
//
//        marketConfig.setOwnerConfig(ownerConfig);
//
//        return marketConfig;
        return null;
    }
}