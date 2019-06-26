package com.clnn.filetool.util;

import com.alibaba.druid.util.StringUtils;

import java.text.MessageFormat;

/**
 * 定义枚举用于限制参数类型
 */
public  class Constant {

    public static String upperFirstChar(String source){
        char[] arrs = source.toCharArray();
        arrs[0] = Character.toUpperCase(arrs[0]);
        return new String(arrs);
    }
    public static String lowerFirstChar(String source){
        char[] arrs = source.toCharArray();
        arrs[0] = Character.toLowerCase(arrs[0]);//注意重新赋值
        return new String(arrs);
    }

    public enum ResultWrapper{
        List,Map,One,Batch;//原理上wrapper不需要考虑map这个场景,额还是要的，若为map,补齐成其他形式
        public static String wrapperMatch(String wrapper){
            if(StringUtils.isEmpty(wrapper)) return null;
            for(ResultWrapper resultWrapper:ResultWrapper.values()){
                if(StringUtils.equalsIgnoreCase(wrapper,resultWrapper.name())) {
                    return resultWrapper.name();
                }
            }
            return null;
        }
    }
    public static String constructMethodName(String property,String prefix) {
        return MessageFormat.format("{0}{1}",prefix,upperFirstChar(property));
    }
}
