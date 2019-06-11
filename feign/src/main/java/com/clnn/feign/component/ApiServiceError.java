package com.clnn.feign.component;

import com.clnn.feign.service.ApiService;
import org.springframework.stereotype.Component;

@Component
public class ApiServiceError implements ApiService {
    @Override
    public String index() {
        return "服务器发生异常";
    }
}
