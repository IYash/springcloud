package com.clnn.filetool.entity.beanDesc;

import com.clnn.filetool.util.Constant;
import com.clnn.filetool.util.EntityConstant;
import lombok.Data;
import org.apache.commons.lang.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class CustomMethod extends CustomBaseEntity {

    private String methodName;//方法名
    private CustomParameterType customParameterType;//返回值
    private List<CustomParameterType> parameterTypeList;//参数列表
    private EntityConstant.CustomVisibility visibility;
    private List<CustomException> customException;
    private boolean withBody = false;

    public String parseParameterTypeList(){
        if(null != getParameterTypeList() && getParameterTypeList().size()>0){
            return getParameterTypeList().stream().map(CustomParameterType::getParameterInfo).collect(Collectors.joining(DOT));
        }
        return "";
    }
    public String parseExceptionList(){
        return getCustomException().stream().map(CustomException::exceptInfo).collect(Collectors.joining(DOT));
    }
    public String buildMethodInfo(){
        StringBuilder sb = new StringBuilder();
        sb.append(getVisibility().name().toLowerCase()).append(SPACE)
                .append(getCustomParameterType().getClzName()).append(SPACE)
                .append(getMethodName()).append(LEFT_BRACKET)
                .append(parseParameterTypeList()).append(RIGHT_BRACKET);
        if(null != customException && customException.size()>0){
            sb.append(SPACE).append("throws").append(SPACE).append(parseExceptionList());
        }
        if(withBody) sb.append(buildMethodInfoWithBody());
        else
            sb.append(SEMICOLO);
        return sb.toString();
    }
    public String buildMethodInfoWithBody(){
        StringBuilder sb = new StringBuilder();
        sb.append(LEFT_PARENTHESIS);
        if(StringUtils.contains(methodName,"get")){
            sb.append("return this").append(POINT).append(Constant.lowerFirstChar(methodName.substring(3))).append(SEMICOLO);
        }else if(StringUtils.contains(methodName,"set")){
            sb.append("this").append(POINT).append(Constant.lowerFirstChar(methodName.substring(3))).append(EQUALS).append(Constant.lowerFirstChar(methodName.substring(3))).append(SEMICOLO);
        }
        sb.append(RIGHT_PARENTHESIS);
        return sb.toString();
    }
    public CustomMethod(CustomMethodBuilder builder){
        this.methodName = builder.getMethodName();
        this.visibility = builder.getVisibility();
        this.customParameterType = builder.getCustomParameterType();
        this.parameterTypeList = builder.getParameterTypeList();
        this.customException = builder.getCustomException();
        this.withBody = builder.isWithBody();
    }

    public CustomMethod() {
    }

    public static CustomMethodBuilder constructBuilder(){
        return new CustomMethodBuilder();
    }
    @Data
    public  static class CustomMethodBuilder {
        private String methodName;//方法名
        private CustomParameterType customParameterType;//返回值
        private List<CustomParameterType> parameterTypeList;//参数列表
        private EntityConstant.CustomVisibility visibility;
        private List<CustomException> customException;
        private boolean withBody;
        public CustomMethodBuilder buildMethodName(String methodName){
            this.methodName = methodName;
            return this;
        }
        public CustomMethodBuilder buildCustomParameterType(CustomParameterType customParameterType){
            this.customParameterType = customParameterType;
            return this;
        }
        public CustomMethodBuilder buildCustomParameterTypeList(List<CustomParameterType> parameterTypeList){
            this.parameterTypeList = parameterTypeList;
            return this;
        }
        public CustomMethodBuilder buildCustomVisibility(EntityConstant.CustomVisibility visibility){
            this.visibility = visibility;
            return this;
        }
        public CustomMethodBuilder buildCustomException(List<CustomException> customException){
            this.customException = customException;
            return this;
        }
        public CustomMethodBuilder buildWithBody(boolean withBody){
            this.withBody = withBody;
            return this;
        }
        public CustomMethod build(){
            return new CustomMethod(this);
        }
    }
}
