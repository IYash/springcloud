package com.clnn.starter;

import com.clnn.cuStarter.HelloService;
import com.clnn.pattern.PatternApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = PatternApplication.class)
public class CuStarterTest {
    @Autowired
    private HelloService helloService;

    @Test
    public void contextLoads() {
    }

    @Test
    public void rere(){
        System.out.println(helloService.sayHello());
    }
}
