package com.clnn.filetool.entity.beanDesc;

import com.clnn.filetool.util.Constant;
import com.clnn.filetool.util.EntityConstant;
import lombok.Data;
import org.apache.commons.lang.StringUtils;

/**
 * 方法参数及返回类型
 */
@Data
public class CustomParameterType extends CustomBaseEntity {

    private String parameterName;
    private String clz;
    private EntityConstant.CustomPrimary inprimary;
    private Constant.ResultWrapper wrapper;//参数额外处理信息，如set，list，map
    private String withAnno;

    public CustomParameterType() {
    }
    public CustomParameterType(CustomParameterTypeBuilder customParameterTypeBuilder){
        this.parameterName = customParameterTypeBuilder.getParameterName();
        this.clz= customParameterTypeBuilder.getClz();
        this.inprimary = customParameterTypeBuilder.getInprimary();
        this.wrapper = customParameterTypeBuilder.getWrapper();
        this.withAnno = customParameterTypeBuilder.getWithAnno();
    }
    public String getClzName(){
        if(null != clz){
            String base = clz.substring(clz.lastIndexOf(CustomBaseEntity.POINT)+1);//这里原来使用Class.forName(),有点逗了
            return wrapBase(base);
        }else if(null != inprimary){
            return inprimary.name();//这边的逻辑其实可以被clz兼容，当前来看是兼容的
        }
        return EntityConstant.CustomPrimary.VOID.name().toLowerCase();
    }
    public String getParameterInfo(){
        return getClzName() +SPACE+ (getParameterName() == null?Constant.lowerFirstChar(getClzName()):getParameterName());//这里拼接字符串需要注意
    }
    private  String wrapBase(String base){
        if(null == wrapper) return base;
        StringBuilder sb =  new StringBuilder();
        switch(wrapper){
            case List:
                wrapperInfo(base, sb);
                break;
            case Map:
                wrapperInfo("String,Object", sb);
                break;
            default:
                break;
        }
        return sb.toString();
    }
    private void wrapperInfo(String base, StringBuilder sb) {
        if(StringUtils.isNotEmpty(withAnno)) sb.append("@Param").
                append(LEFT_BRACKET).append(DOUBLE_QUOTATION)
                .append(withAnno).append(DOUBLE_QUOTATION)
                .append(RIGHT_BRACKET);
        sb.append(wrapper.name())
                .append(CustomBaseEntity.ANGLE_LBRACKET)
                .append(base)
                .append(CustomBaseEntity.ANGLE_RBRACKET);
    }

    public static CustomParameterTypeBuilder constructBuilder(){
        return new CustomParameterTypeBuilder();
    }
    @Data
    public static class CustomParameterTypeBuilder{
        private String parameterName;
        private String clz;
        private EntityConstant.CustomPrimary inprimary;
        private Constant.ResultWrapper wrapper;
        private String withAnno;
        public CustomParameterTypeBuilder buildParameterName(String parameterName){
            this.parameterName = parameterName;
            return this;
        }
        public CustomParameterTypeBuilder buildClz(String clz){
            this.clz = clz;
            return this;
        }
        public CustomParameterTypeBuilder buildInprimary(EntityConstant.CustomPrimary inprimary){
            this.inprimary = inprimary;
            return this;
        }
        public CustomParameterTypeBuilder buildWrapper(Constant.ResultWrapper wrapper){
            this.wrapper = wrapper;
            return this;
        }
        public CustomParameterTypeBuilder buildWithAnno(String withAnno){
            this.withAnno = withAnno;
            return this;
        }
        public CustomParameterType build(){
            return new CustomParameterType(this);
        }
    }
}
