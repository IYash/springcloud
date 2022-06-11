package com.clnn.feign.component;

import com.clnn.feign.service.ApiService;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ApiServiceError implements ApiService {
    @Override
    public String index() {
        return "服务器发生异常";
    }

    @Override
    public String param(String tenantCode, Map<String, String> params) {
        return null;
    }
}
