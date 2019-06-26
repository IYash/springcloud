package com.clnn.filetool.domain;

import com.clnn.filetool.entity.beanDesc.*;
import com.clnn.filetool.entity.xmlDesc.CustomLabelInfo;
import com.clnn.filetool.entity.xmlDesc.CustomMapper;
import com.clnn.filetool.util.Constant;
import com.clnn.filetool.util.EntityConstant;
import com.clnn.filetool.util.MybatisXmlParser;
import com.clnn.filetool.util.XmlMapperConstant;
import lombok.Data;
import org.apache.commons.lang.StringUtils;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;

/**
 *基本桥接
 */
@Data
public abstract class BaseSupport {

    protected final static String LIST="java.util.List";
    protected final static String MAP="java.util.Map";

    private CustomMapper mapper;

    private Map<String,String> resultMap = new HashMap<>();

    private void buildMap(){
            mapper.getResultMapList().stream().forEach(customResultMap -> {
            resultMap.put(customResultMap.fetchCustomAttributeAttr(XmlMapperConstant.LabelAttr.id.name()), customResultMap.fetchCustomAttributeAttr(XmlMapperConstant.LabelAttr.type.name()));
        });
    }
    public String fetchResultType(String resultMapId){
        return resultMap.get(resultMapId);
    }

    private String sourcePath;//待解析文件路径

    private Set<String> importInfos = new HashSet<>();

    public BaseSupport() {
    }

    public BaseSupport(String sourcePath) {
        this.sourcePath = sourcePath;
    }

    @PostConstruct //需要结合框架使用，也可以直接调用
    public void parseMapper(){
        mapper = MybatisXmlParser.parseXml(sourcePath);
        buildMap();
    }

    /**
     * 导入信息构造
     * @param clz
     * @return
     */
    protected CustomImport buildCustomImport(String clz){
        return CustomImport.constructBuilder().buildQualifiedName(clz).build();
    }
    public List<CustomImport> buildCustomImports(){
        return getImportInfos().stream().map(
                this::buildCustomImport
        ).collect(Collectors.toList());
    }
    protected void addImportInfo(String importInfo){
        if(StringUtils.isNotEmpty(importInfo)) importInfos.add(importInfo);
    }
    protected CustomParameterType buildCustomParameterType(String clz, String parameterName, Constant.ResultWrapper wrapper, String withAnno){
        return CustomParameterType.constructBuilder().buildClz(clz).buildParameterName(parameterName)
                .buildWrapper(wrapper).buildWithAnno(withAnno).build();
    }

    public abstract CustomClz buildCustomClz();
    public abstract List<CustomMethod> buildCustomMethods();
    public abstract List<CustomProperty> buildCustomProperties();
    public abstract List<CustomPropertyWithMethod> buildPropertyWithMethods();
    protected String buildResultCustomParameterType(CustomLabelInfo label){
        String result ;
        result = parseParameterAttr(label,XmlMapperConstant.LabelAttr.type.name());
        if(!StringUtils.isEmpty(result)){
            return result;
        }
        result = parseParameterAttr(label,XmlMapperConstant.LabelAttr.resultType.name());
        if(!StringUtils.isEmpty(result)){
            String type = EntityConstant.CustomPrimary.primaryMatch(result);
            return  type== null?result:type;
        }
        result =  parseParameterAttr(label,XmlMapperConstant.LabelAttr.resultMap.name());//获取resultMap id
        if(!StringUtils.isEmpty(result)){
            result = fetchResultType(result);
            return result;
        }
        return null;
    }
    protected String parseParameterAttr(CustomLabelInfo label,String attrName){
        if(null == label) return null;
        return label.fetchCustomAttributeAttr(attrName);
    }
}
