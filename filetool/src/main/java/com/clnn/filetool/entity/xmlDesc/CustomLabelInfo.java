package com.clnn.filetool.entity.xmlDesc;

import lombok.Data;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


/**
 * 用于描述标签信息，包括resultMap,sql，insert,delete,update,select等
 */
@Data
public class CustomLabelInfo extends XmlBaseEntity {
    private String body;
    private CustomLabelInfo customLabelInfo;//用于递归处理
    private List<CustomLabelInfo> customLabelInfoList;

    public List<CustomLabelInfo> specifyCustomLabelInfo(String label){
        if(null == customLabelInfoList || customLabelInfoList.size()==0) return new ArrayList();
        return customLabelInfoList.stream().filter(labelInfo -> StringUtils.equals(labelInfo.getLabel(),label)).collect(Collectors.toList());
    }
}
