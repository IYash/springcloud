package com.clnn.filetool.entity.beanDesc;


import com.clnn.filetool.util.EntityConstant;
import lombok.Data;
import org.apache.commons.lang.StringUtils;

/**
 * 这是要生成的类，只能给定类名
 */
@Data
public class CustomClz extends CustomBaseEntity {


    private String clz;
    private EntityConstant.CustomVisibility visibility;
    private String HEAD;
    private String withAnno;

    public CustomClz() {
    }

    public CustomClz(CustomClzBuilder customClzBuilder) {
        this.clz = customClzBuilder.getClz();
        this.visibility = customClzBuilder.getVisibility();
        this.HEAD = customClzBuilder.getHEAD();
        this.withAnno = customClzBuilder.getWithAnno();
    }
    public String buildConstruct(){
        StringBuilder sb = new StringBuilder();
        if(StringUtils.isNotEmpty(withAnno)) sb.append(withAnno).append(CRLF);
        sb.append(getVisibilityStr()).append(SPACE)
        .append(getHEAD()).
                append(SPACE)
                .append(getClz()).append(LEFT_PARENTHESIS);
        return sb.toString();
     }
    private String getVisibilityStr(){
        return getVisibility() ==null ?"":getVisibility().name().toLowerCase();
    }

    public static CustomClzBuilder constructBuilder(){
        return new CustomClzBuilder();
    }
    @Data
    public static class CustomClzBuilder{
        private String clz;
        private EntityConstant.CustomVisibility visibility;
        private String HEAD;
        private String withAnno;
        public CustomClzBuilder buildClz(String clz){
            this.clz = clz;
            return this;
        }
        public CustomClzBuilder buildVisibility(EntityConstant.CustomVisibility visibility){
            this.visibility =visibility;
            return this;
        }
        public CustomClzBuilder buildHead(String head){
            this.HEAD = head;
            return this;
        }
        public CustomClzBuilder buildWithAnno(String withAnno){
            this.withAnno = withAnno;
            return this;
        }
        public CustomClz build(){
            return new CustomClz(this);
        }
    }

}
