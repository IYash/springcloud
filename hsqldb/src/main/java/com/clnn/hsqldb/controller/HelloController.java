package com.clnn.hsqldb.controller;

import com.clnn.hsqldb.entity.Pkonly;
import com.clnn.hsqldb.service.PkonlyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class HelloController {


    @Autowired
    private PkonlyService pkonlyService;

    @PostMapping("addPkonly")
    public String addPkonly(@RequestBody  Pkonly pkonly){
        pkonlyService.addPkonly(pkonly);
        return "success";
    }

    @GetMapping("showPkonlys")
    public List<Pkonly> showPkonlys(){
        return pkonlyService.fetchPkonlys();
    }
}
