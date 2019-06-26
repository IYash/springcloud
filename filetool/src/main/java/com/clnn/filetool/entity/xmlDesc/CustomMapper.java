package com.clnn.filetool.entity.xmlDesc;

import lombok.Data;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 用于描述mapper信息
 */
@Data
public class CustomMapper extends XmlBaseEntity {
    private List<CustomLabelInfo> resultMapList;
    private List<CustomLabelInfo> labelInfos;
    private List<CustomLabelInfo> otherLabels;//存储其他的标签信息
    public CustomMapper() {
        labelInfos = new ArrayList<>();
        resultMapList = new ArrayList<>();
        otherLabels = new ArrayList<>();
    }

    public void addCustomLabel(CustomLabelInfo labelInfo){
        labelInfos.add(labelInfo);
    }
    public List<CustomLabelInfo> constructMapList() {

        Iterator<CustomLabelInfo> its = labelInfos.iterator();
        while(its.hasNext()){
            CustomLabelInfo labelInfo = its.next();
            String namespace = labelInfo.getLabel();
            if(StringUtils.equalsIgnoreCase(namespace,RESULTMAP)){
                CustomLabelInfo resultMap = new CustomLabelInfo();
                resultMap.setLabel(labelInfo.getLabel());
                resultMap.setAttribute(labelInfo.getAttribute());
                resultMap.setCustomLabelInfoList(labelInfo.getCustomLabelInfoList());
                resultMapList.add(resultMap);
                its.remove();
            }else if(!ACCEPT_METHOD_LABEL.contains(namespace)){
               otherLabels.add(labelInfo);
               its.remove();
            }
        }
        return resultMapList;
    }
}
