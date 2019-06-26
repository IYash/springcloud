package com.clnn.filetool.entity.beanDesc;

import com.clnn.filetool.util.EntityConstant;
import lombok.Data;


/**
 * 属性的描述对象
 */
@Data
public class CustomProperty extends CustomBaseEntity{
    private CustomParameterType parameter;
    private EntityConstant.CustomVisibility visibility;

    public CustomProperty(){}

    public CustomProperty(CustomPropertyBuilder customPropertyBuilder){
        this.parameter = customPropertyBuilder.getParameter();
        this.visibility = customPropertyBuilder.getVisibility();
    }
    public String parsePropertyName(){
        return getParameter().getParameterName();
    }
    public static CustomPropertyBuilder constructBuilder(){
        return new CustomPropertyBuilder();
    }
    @Data
    public static class CustomPropertyBuilder{
        private CustomParameterType parameter;
        private EntityConstant.CustomVisibility visibility;
        public CustomPropertyBuilder buildParameter(CustomParameterType parameter){
            this.parameter = parameter;
            return this;
        }
        public CustomPropertyBuilder buildVisibility(EntityConstant.CustomVisibility visibility){
            this.visibility = visibility;
            return this;
        }
        public CustomProperty build(){
            return new CustomProperty(this);
        }
    }
    public String buildPropertyInfo(){
        StringBuilder sb = new StringBuilder();
        sb.append(getVisibility().name().toLowerCase())
                .append(SPACE)
                .append(getParameter().getParameterInfo())
                .append(SEMICOLO);
        return sb.toString();
    }
}
