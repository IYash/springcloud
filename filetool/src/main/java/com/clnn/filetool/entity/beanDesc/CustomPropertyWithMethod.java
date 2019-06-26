package com.clnn.filetool.entity.beanDesc;

import com.clnn.filetool.util.Constant;
import com.clnn.filetool.util.EntityConstant;
import lombok.Data;

import java.util.Arrays;


/**
 * 标记属性和方法映射关系
 */
@Data
public class CustomPropertyWithMethod extends CustomBaseEntity{

    private CustomProperty customProperty;
    private CustomMethod readMethod;
    private CustomMethod writeMethod;

    public CustomPropertyWithMethod() {
    }
    public CustomPropertyWithMethod(CustomPropertyWithMethodBuilder propertyWithMethodBuilder){
        this.customProperty = propertyWithMethodBuilder.getCustomProperty();
        buildWriteMethod();
        buildReadMethod();
    }
    private void buildWriteMethod(){
        String methodName = Constant.constructMethodName(getCustomProperty().parsePropertyName(),"set");
        CustomParameterType clzInfo = getCustomProperty().getParameter();
        writeMethod = CustomMethod.constructBuilder().buildCustomVisibility(EntityConstant.CustomVisibility.PUBLIC)
                .buildMethodName(methodName).buildCustomParameterType(CustomParameterType.constructBuilder().build())
                .buildCustomParameterTypeList(Arrays.asList(clzInfo)).buildWithBody(true).build();

    }
    public void buildReadMethod(){
        String methodName = Constant.constructMethodName(getCustomProperty().parsePropertyName(),"get");
        CustomParameterType clzInfo = getCustomProperty().getParameter();
        readMethod = CustomMethod.constructBuilder().buildCustomVisibility(EntityConstant.CustomVisibility.PUBLIC)
                .buildCustomParameterType(clzInfo).buildMethodName(methodName).buildWithBody(true).build();

    }
    public static CustomPropertyWithMethodBuilder constructBuilder(){
        return new CustomPropertyWithMethodBuilder();
    }
    @Data
    public static class CustomPropertyWithMethodBuilder{
        private CustomProperty customProperty;
        public CustomPropertyWithMethodBuilder buildCustomProperty(CustomProperty customProperty){
            this.customProperty = customProperty;
            return this;
        }
        public CustomPropertyWithMethod build(){
            return new CustomPropertyWithMethod(this);
        }
    }
    public String buildCustomPropertyInfo(){
        StringBuilder sb = new StringBuilder();
        sb.append(getCustomProperty().buildPropertyInfo());
        return sb.toString();
    }
    public String buildCustomMethodInfo(){
        StringBuilder sb = new StringBuilder();
        sb
                .append(getWriteMethod().buildMethodInfo())
                .append(getReadMethod().buildMethodInfo());
        return sb.toString();
    }
}
