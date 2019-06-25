package com.game.property.bean;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Component;

/**
 * @ClassName Property
 * @Description 属性实体类
 * @Author DELL
 * @Date 2019/6/20 21:34
 * @Version 1.0
 */
@Component
public class Property {
    private Integer id;
    private JSONObject propertyJson;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public JSONObject getPropertyJson() {
        return propertyJson;
    }

    public void setPropertyJson(JSONObject propertyJson) {
        this.propertyJson = propertyJson;
    }
}
