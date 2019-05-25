package com.clnn.gateway.handler;

import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.ResourceProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.DefaultErrorWebExceptionHandler;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.server.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 自定义异常处理
 * SpringBoot提供了默认的异常处理类，显然不符合预期
 * 因此需要重写此类，返回统一的JSON格式
 */
public class JsonExceptionHandler extends DefaultErrorWebExceptionHandler {

    public JsonExceptionHandler(ErrorAttributes errorAttributes, ResourceProperties resourceProperties, ErrorProperties errorProperties, ApplicationContext applicationContext) {
        super(errorAttributes, resourceProperties, errorProperties, applicationContext);
    }

    /**
     * 获取异常类属性
     * @param request
     * @param includesStackTrace
     * @return
     */
    @Override
    protected Map<String,Object> getErrorAttributes(ServerRequest request, boolean includesStackTrace){
        int code = 500;
        Throwable error = super.getError(request);
        if(error instanceof org.springframework.cloud.gateway.support.NotFoundException){
            code = 404;
        }
        return response(code,this.buildMessage(request,error));
    }

    /**
     * 指定响应处理方法为JSON处理方法
     * @param errorAttributes
     * @return
     */
    @Override
    protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes){
        return RouterFunctions.route(RequestPredicates.all(),this::renderErrorResponse);
    }

    /**
     * 根据code获取对应的HttpStatus
     * @param errorAttributes
     * @return
     */
    @Override
    protected HttpStatus getHttpStatus(Map<String,Object> errorAttributes){
        int statusCode = (int) errorAttributes.get("code");
        return HttpStatus.valueOf(statusCode);
    }
    /**
     * 构建返回的JSON数据格式
     * @param status
     * @param errorMessage
     * @return
     */
    private Map<String,Object> response(int status, String errorMessage) {
        Map<String,Object> map = new HashMap<>();
        map.put("code",status);
        map.put("message",errorMessage);
        map.put("data",null);
        return map;
    }

    /**
     * 构建异常信息
     * @param request
     * @param error
     * @return
     */
    private String buildMessage(ServerRequest request, Throwable error) {
        StringBuilder message = new StringBuilder("Failed to handle request [");
        message.append(request.methodName())
                .append(" ")
                .append(request.uri())
                .append("]");
        if(error != null){
            message.append(":" )
                    .append(error.getMessage());
        }
        return message.toString();
    }
}
