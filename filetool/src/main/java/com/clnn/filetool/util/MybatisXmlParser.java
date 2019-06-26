package com.clnn.filetool.util;

import com.clnn.filetool.entity.xmlDesc.CustomAttribute;
import com.clnn.filetool.entity.xmlDesc.CustomLabelInfo;
import com.clnn.filetool.entity.xmlDesc.CustomMapper;
import com.clnn.filetool.entity.xmlDesc.XmlBaseEntity;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 用于解析xml对象
 */
public class MybatisXmlParser {

    public static CustomMapper parseXml(String path){

        SAXReader reader = new SAXReader();
        try {
            Document document = reader.read(new File(path));
            Element xmlStore = document.getRootElement();
            //解析根节点 begin
            CustomMapper mapper = new CustomMapper();
            parseCustomElement(xmlStore,mapper);
            //end
            Iterator<Element> it = xmlStore.elementIterator();
            while (it.hasNext()) {
                CustomLabelInfo customLabelInfo = buildCustomLabelInfo(it.next());
                mapper.addCustomLabel(customLabelInfo);
            }
            mapper.constructMapList();
            return mapper;
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 递归解析
     * @param element
     * @return
     */
    private static CustomLabelInfo buildCustomLabelInfo(Element element){
        CustomLabelInfo customLabelInfo = (CustomLabelInfo) parseCustomElement(element,new CustomLabelInfo());
        customLabelInfo.setBody(element.getText());
        Iterator<Element> it = element.elementIterator();
        List<CustomLabelInfo> infos = new ArrayList<>(); //todo
        while(it.hasNext()){
           Element elIt = it.next();
           //customLabelInfo.setCustomLabelInfo(buildCustomLabelInfo(elIt));
           infos.add(buildCustomLabelInfo(elIt));
        }
        customLabelInfo.setCustomLabelInfoList(infos);
       return customLabelInfo;
    }
    private static XmlBaseEntity parseCustomElement(Element element, XmlBaseEntity baseModel) {
        baseModel.setLabel(element.getName());
        List<Attribute> eleAttrs = element.attributes();
        CustomAttribute customAttribute = new CustomAttribute();
        for (Attribute attr : eleAttrs) {
            customAttribute.addAttr(attr.getName(),attr.getStringValue());
        }
        baseModel.setAttribute(customAttribute);
        return baseModel;
    }

    public static void main(String[] args) {
        MybatisXmlParser parser = new MybatisXmlParser();
        CustomMapper mapper = parser.parseXml("E:\\PkonlyMapper.xml");
        System.out.println(mapper.getLabel());
        mapper.getLabelInfos().stream().forEach(CustomLabelInfo::getBody);
    }
}
