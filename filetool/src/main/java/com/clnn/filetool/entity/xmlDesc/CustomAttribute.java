package com.clnn.filetool.entity.xmlDesc;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class CustomAttribute {
    private Map<String,String> attrMap;

    public CustomAttribute() {
        attrMap = new HashMap<>();
    }

    public void addAttr(String attrName, String attrVal){
        attrMap.put(attrName,attrVal);
    }

    public String fetchAttr(String attrName){ return attrMap.get(attrName);}
}
