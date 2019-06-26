package com.clnn.filetool.entity.xmlDesc;

import lombok.Data;

@Data
public class XmlBaseEntity {
    public static final String RESULTMAP="resultMap";
    public static final String ACCEPT_METHOD_LABEL="insert,update,delete,select";
    private String label;
    private CustomAttribute attribute;
    public String fetchCustomAttributeAttr(String attrName){
        return getAttribute().fetchAttr(attrName);
    }
}
