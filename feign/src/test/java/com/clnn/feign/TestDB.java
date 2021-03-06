package com.clnn.feign;

import com.clnn.feign.service.ApiService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest(classes = FeignApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class TestDB {
    @Autowired
    private ApiService apiService;

    @Test
    public void test(){
        try {
//            System.out.println(apiService.index());
            Map<String,String> params = new HashMap<>();
            params.put("hello","world");
            System.out.println(apiService.param("1234",params));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
