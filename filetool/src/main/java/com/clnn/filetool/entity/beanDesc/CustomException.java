package com.clnn.filetool.entity.beanDesc;

import lombok.Data;

@Data
public class CustomException extends CustomBaseEntity {

    private Exception exception;

    public CustomException() {
    }
    public CustomException(CustomExceptionBuilder customExceptionBuilder){
        this.exception = customExceptionBuilder.getException();
    }
    public String exceptInfo(){
        return getException().getClass().getSimpleName();
    }

    public static CustomExceptionBuilder constructBuilder(){
        return new CustomExceptionBuilder();
    }
    @Data
    public static class CustomExceptionBuilder{
        private Exception exception;
        public CustomExceptionBuilder buildException(Exception exception){
            this.exception = exception;
            return this;
        }
        public CustomException build(){
            return new CustomException(this);
        }
    }
}
