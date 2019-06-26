package com.clnn.filetool.domain;

import com.clnn.filetool.entity.beanDesc.CustomClz;
import com.clnn.filetool.entity.beanDesc.CustomMethod;
import com.clnn.filetool.entity.beanDesc.CustomProperty;
import com.clnn.filetool.entity.beanDesc.CustomPropertyWithMethod;
import com.clnn.filetool.util.EntityConstant;
import com.clnn.filetool.util.JdbcTypeMapping;
import com.clnn.filetool.util.XmlMapperConstant;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class CustomEntitySupport extends BaseSupport{

    public CustomEntitySupport() {
    }

    public CustomEntitySupport(String sourcePath) {
        super(sourcePath);
    }

    /**
     * 类名称构造
     * @return
     */

    @Override
    public CustomClz buildCustomClz(){
        String clzName = buildResultCustomParameterType(getMapper().getResultMapList().get(0));
        return CustomClz.constructBuilder().buildClz(clzName.substring(clzName.lastIndexOf(".")+1))
                .buildHead(EntityConstant.CustomType.CLASS.name().toLowerCase())
                .buildVisibility(EntityConstant.CustomVisibility.PUBLIC).build();
    }

    @Override
    public List<CustomMethod> buildCustomMethods() {
        return null;
    }

    /**
     * 属性构建,只进行基本属性处理
     * @return
     */
    @Override
    public List<CustomProperty> buildCustomProperties(){
        return getMapper().getResultMapList().get(0).getCustomLabelInfoList().stream().map(
                labelInfo -> {
                    String property = labelInfo.fetchCustomAttributeAttr(XmlMapperConstant.LabelAttr.property.name());
                    String jdbcType = labelInfo.fetchCustomAttributeAttr(XmlMapperConstant.LabelAttr.jdbcType.name());
                    String javaType = JdbcTypeMapping.mappingJavaType(jdbcType);
                    getImportInfos().add(javaType);
                    return CustomProperty.constructBuilder().buildParameter(buildCustomParameterType(javaType,property,null,null)).
                            buildVisibility(EntityConstant.CustomVisibility.PRIVATE).build();
                }
        ).collect(Collectors.toList());
    }

    /**
     * 属性及对应方法构建
     * @return
     */
    @Override
    public List<CustomPropertyWithMethod> buildPropertyWithMethods(){
        List<CustomProperty> properties = buildCustomProperties();
        return properties.stream().map(customProperty -> {
            return CustomPropertyWithMethod.constructBuilder().buildCustomProperty(customProperty).build();
        }).collect(Collectors.toList());
    }
}
