package com.test.load;

import java.util.Properties;

/**
 *
 *
 * @author Bean
 * @date 2016年1月21日 下午11:24:50
 * @version 1.0
 *
 */
public class OwnerConfigLoader extends AbstractConfigLoader<OwnerConfig, Properties>{

    /**
     * @param provider
     */
    protected OwnerConfigLoader(IConfigProvider<Properties> provider) {
        super(provider);
    }

    /* 
     * @see AbstractConfigLoader#load(java.lang.Object)
     */
    @Override
    public OwnerConfig load(Properties props) throws Exception {
        OwnerConfig ownerConfig = new OwnerConfig();
        
        /**
         * 利用props，设置ownerConfig的属性值
         * 
         * 此处代码省略
         */
        return ownerConfig;
    }
}
