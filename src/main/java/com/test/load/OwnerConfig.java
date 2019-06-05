package com.test.load;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 *
 *
 * @author Bean
 * @date 2016年1月21日 下午11:04:06
 * @version 1.0
 *
 */
public class OwnerConfig {

    private Map<String, String> owner = new HashMap<String, String>();
    
    public void addOwner(String portName, String owner) {
        this.owner.put(portName, owner);
    }
    
    public String getOwnerByPortName(String portName) {
        return this.owner.get(portName);
    }
    
    public Map<String, String> getOwners() {
        return Collections.unmodifiableMap(this.owner);
    }
}