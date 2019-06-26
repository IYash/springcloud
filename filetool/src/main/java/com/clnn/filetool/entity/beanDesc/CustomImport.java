package com.clnn.filetool.entity.beanDesc;

import lombok.Data;

@Data
public class CustomImport extends CustomBaseEntity {


    private String qualifiedName;
    public String getFullyQualifiedName(){
        try {
            return Class.forName(qualifiedName).getCanonicalName();//使用Class.forName的逗子，容易出问题，运行时需要添加生成的class，用全限定名是简单的
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return getQualifiedName();
    }

    public CustomImport() {
    }
    public CustomImport(CustomImportBuilder customImportBuilder){
        this.qualifiedName = customImportBuilder.getQualifiedName();
    }
    public String buildImportInfo(){
        return "import"+SPACE+getFullyQualifiedName();
    }
    public static CustomImportBuilder constructBuilder(){
        return new CustomImportBuilder();
    }
    @Data
    public static class CustomImportBuilder{
        private String qualifiedName;
        public CustomImportBuilder buildQualifiedName(String qualifiedName){
            this.qualifiedName = qualifiedName;
            return this;
        }
        public CustomImport build(){
            return new CustomImport(this);
        }
    }
}
