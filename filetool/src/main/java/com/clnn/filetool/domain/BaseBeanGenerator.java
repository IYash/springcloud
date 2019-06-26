package com.clnn.filetool.domain;

import com.clnn.filetool.entity.beanDesc.CustomPropertyWithMethod;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class BaseBeanGenerator extends BaseGenerator {
    /**
     * 属性及方法
     * @return
     */
    private List<CustomPropertyWithMethod> propertyWithMethods;
    @Override
    protected String specifyBody() {
        StringBuilder sb = new StringBuilder();
        String properties = propertyWithMethods.stream().map(CustomPropertyWithMethod::buildCustomPropertyInfo).collect(Collectors.joining());
        String body = propertyWithMethods.stream().map(CustomPropertyWithMethod::buildCustomMethodInfo).collect(Collectors.joining());
        sb.append(properties);
        sb.append(body);
        return sb.toString();
    }
}
