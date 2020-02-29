package com.clnn.feign.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

@FeignClient(value = "eurekaclient")
public interface ApiService {

    @RequestMapping(value = "/index",method = RequestMethod.GET)
    String index();

    @RequestMapping(value = "param",method = RequestMethod.POST)
    String param(@RequestHeader("tenantCode") String tenantCode ,Map<String,String> params);

}
