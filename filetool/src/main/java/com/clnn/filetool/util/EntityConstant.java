package com.clnn.filetool.util;

import com.alibaba.druid.util.StringUtils;

/**
 * java对象属性的常量管理
 */
public class EntityConstant {

    public static enum CustomPrimary{

        BYTE,SHORT,CHAR,INT,LONG,FLOAT,DOUBLE,BOOLEAN,VOID;

        public static String primaryMatch(String primary){
            if(StringUtils.isEmpty(primary)) return null;
            for(CustomPrimary customPrimary:CustomPrimary.values()){
                if(StringUtils.equalsIgnoreCase(primary,customPrimary.name())){
                    return customPrimary.name().toLowerCase();
                }
            }
            return null;
        }
    }
    public enum CustomVisibility {
        PRIVATE,PROTECT,PUBLIC
    }

    public enum CustomType{
        CLASS,INTERFACE,ENUM;
    }

}
