package com.test.load;

/**
 *
 *
 * @author Bean
 * @date 2016年1月21日 下午11:03:37
 * @version 1.0
 *
 */
public class MarketConfig {

    private AppleConfig appleConfig;
    private EggConfig eggConfig;
    private OwnerConfig ownerConfig;
    
    public AppleConfig getAppleConfig() {
        return appleConfig;
    }
    public void setAppleConfig(AppleConfig appleConfig) {
        this.appleConfig = appleConfig;
    }
    public EggConfig getEggConfig() {
        return eggConfig;
    }
    public void setEggConfig(EggConfig eggConfig) {
        this.eggConfig = eggConfig;
    }
    public OwnerConfig getOwnerConfig() {
        return ownerConfig;
    }
    public void setOwnerConfig(OwnerConfig ownerConfig) {
        this.ownerConfig = ownerConfig;
    }
}