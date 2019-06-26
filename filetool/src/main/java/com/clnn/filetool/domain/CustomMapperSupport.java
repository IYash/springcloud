package com.clnn.filetool.domain;

import com.clnn.filetool.entity.beanDesc.*;
import com.clnn.filetool.entity.xmlDesc.CustomLabelInfo;
import com.clnn.filetool.util.*;
import lombok.Data;
import org.apache.commons.lang.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

@Data
public class CustomMapperSupport extends BaseSupport{

    public CustomMapperSupport() {
    }

    public CustomMapperSupport(String sourcePath) {
        super(sourcePath);
    }

    /**
     * 方法构造
     * @param label
     * @return
     * @throws Exception
     */
    private CustomMethod buildCustomMethod(CustomLabelInfo label){

        String clzInfo = buildResultCustomParameterType(label);
        return CustomMethod.constructBuilder().buildCustomVisibility(EntityConstant.CustomVisibility.PUBLIC)
                .buildMethodName(label.getAttribute().fetchAttr(XmlMapperConstant.LabelAttr.id.name()))
                .buildCustomParameterTypeList(buildCustomParameterTypeList(label))//根据map或者单一类型
                .buildCustomException(
                        Arrays.asList(CustomException.constructBuilder().buildException(new RuntimeException("")).build())
                )
                .buildCustomParameterType(buildCustomParameterType(clzInfo,null,buildWrapper(label,clzInfo,XmlMapperConstant.LabelAttr.resultType),null))
                .build();
    }

    private List<CustomParameterType> buildCustomParameterTypeList(CustomLabelInfo label){
        String parameterName = null;
        String withAnno = null;
        //基本参数及map特殊处理
        String parameterInfo = parseParameterAttr(label,XmlMapperConstant.LabelAttr.parameterType.name());
        if(StringUtils.equals(parameterInfo,"map")) {parameterInfo = MAP;parameterName="parameterMap";}
        if(!StringUtils.contains(parameterInfo,".")) {
            parameterName = "primaryParam";
        }else{
            addImportInfo(parameterInfo);
        }
        List<CustomParameterType> cpts = new ArrayList<>();
        if(StringUtils.isEmpty(parameterInfo)) return cpts;
        final String parameterInfoInn = parameterInfo;
        List<CustomLabelInfo> customLabelInfos = label.specifyCustomLabelInfo("foreach");
        if(customLabelInfos.size()>0){
        return customLabelInfos.stream().map(labelInfo -> {
            String parameterNameInn = buildParameterName(labelInfo);
            String withAnnoInn = parameterNameInn;
            return buildCustomParameterType(parameterInfoInn,parameterNameInn,buildWrapper(label,parameterInfoInn,XmlMapperConstant.LabelAttr.parameterType),withAnnoInn);
        }).collect(Collectors.toList());
        }else{
            cpts.add(buildCustomParameterType(parameterInfo,parameterName,buildWrapper(label,parameterInfo,XmlMapperConstant.LabelAttr.parameterType),withAnno));
            return cpts;
        }
    }
    //用于获取自定义的变量名称
    private String buildParameterName(CustomLabelInfo label){
        String parameterName = null;
        parameterName = parseParameterAttr(label,XmlMapperConstant.LabelAttr.collection.name());
        return parameterName;
    }
    private Constant.ResultWrapper buildWrapper(CustomLabelInfo label,String clzInfo,XmlMapperConstant.LabelAttr attr){
        Constant.ResultWrapper wrapper = null;
        if(StringUtils.equalsIgnoreCase(clzInfo,MAP)){
            getImportInfos().add(clzInfo);
            return Constant.ResultWrapper.Map;
        }
        String id;
        switch(attr){
            case resultType:
                id= parseParameterAttr(label,XmlMapperConstant.LabelAttr.id.name());
                if(!StringUtils.containsIgnoreCase(id,Constant.ResultWrapper.One.name())){
                    wrapper=Constant.ResultWrapper.List;
                    getImportInfos().add(LIST);
                }
                break;
            case parameterType:
                id = parseParameterAttr(label,XmlMapperConstant.LabelAttr.id.name());
                if(StringUtils.containsIgnoreCase(id,Constant.ResultWrapper.Batch.name())){
                    wrapper=Constant.ResultWrapper.List;
                    getImportInfos().add(LIST);
                }
                break;
            default:
                break;
        }
        return wrapper;
    }

    /**
     * 接口名称构造
     * @return
     */
    @Override
    public CustomClz buildCustomClz(){
        String clzName = getMapper().fetchCustomAttributeAttr(XmlMapperConstant.LabelAttr.namespace.name());
        return CustomClz.constructBuilder().buildClz(clzName.substring(clzName.lastIndexOf(".")+1))
                .buildHead(EntityConstant.CustomType.INTERFACE.name().toLowerCase())
                .buildVisibility(EntityConstant.CustomVisibility.PUBLIC).build();
    }
    @Override
    public List<CustomMethod> buildCustomMethods(){
        return getMapper().getLabelInfos().stream().map(
                this::buildCustomMethod
        ).collect(Collectors.toList());
    }

    @Override
    public  List<CustomProperty> buildCustomProperties() {
        return null;
    }

    @Override
    public List<CustomPropertyWithMethod> buildPropertyWithMethods() {
        return null;
    }
}
