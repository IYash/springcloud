package com.clnn.filetool.domain;

import com.clnn.filetool.entity.beanDesc.CustomMethod;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 用于构建java文件对象
 */
@Data
public class MapperGenerator extends BaseGenerator {
    /**
     * 方法
     */
    private List<CustomMethod> customMethods;


    @Override
    protected String specifyBody() {
        return customMethods.stream().map(CustomMethod::buildMethodInfo).collect(Collectors.joining());
    }
}
