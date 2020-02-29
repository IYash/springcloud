package com.clnn.eurekaclient.controller;

import com.clnn.eurekaclient.service.GroovyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Map;

/**
 * 吐槽：
 * 一个项目可能会包含很多个服务，每个服务的端口和ip都可能不一样。
 * 那么如果以这种方式提供接口给外部调用，代价是非常大的。
 * 从安全性上考虑，系统对外提供的接口应该进行合法性校验，防止非法请求，如果按照
 * 这种形式，那每个服务都要写一遍校验规则，维护起来也很麻烦
 */
@RestController
public class HelloController {

    @Value("${server.port}")
    private int port;

    @Autowired
    private GroovyService groovyService;
    @GetMapping("index")
    public String index(){
        return "hello world,port:"+port;
    }


    @PostMapping("param")
    public String param(HttpServletRequest request, @RequestBody Map<String, String> params){
        String tenantHead = request.getHeader("tenantCode");
        System.out.println(tenantHead);
        StringBuilder sb = new StringBuilder(100);
        params.entrySet().stream().forEach(entry->sb.append(entry.getKey()).append(entry.getValue()).append(";"));
        return sb.toString();
    }

    @PostMapping("groovyS")
    //直接从request获取参数
    public void groovy(HttpServletRequest request) throws Exception{
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
            String tmp;
            StringBuilder sb = new StringBuilder();
            while ((tmp = reader.readLine()) != null) {
                sb.append(tmp);
                System.out.println(tmp);
            }
            groovyService.initial2Groovy(sb.toString());
        }catch(Exception e){
            System.out.println(e.getMessage());
        } finally {
            reader.close();
        }

    }
}
