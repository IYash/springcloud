package com.clnn.filetool.domain;

import com.clnn.filetool.entity.beanDesc.CustomProperty;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class EntityGenerator extends BaseGenerator {
    /**
     * 属性
     */
    private List<CustomProperty> customProperties;


    @Override
    protected String specifyBody() {
        return customProperties.stream().map(CustomProperty::buildPropertyInfo).collect(Collectors.joining());
    }
}
