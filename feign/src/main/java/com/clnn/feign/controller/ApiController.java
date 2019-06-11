package com.clnn.feign.controller;

import com.clnn.feign.service.ApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiController {

    @Autowired
    private ApiService apiService;

    @GetMapping("index")
    public String index(){
        return apiService.index();
    }
}
