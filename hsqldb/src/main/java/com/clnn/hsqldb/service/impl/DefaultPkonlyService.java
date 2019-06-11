package com.clnn.hsqldb.service.impl;

import com.clnn.hsqldb.entity.Pkonly;
import com.clnn.hsqldb.mapper.PkonlyMapper;
import com.clnn.hsqldb.service.PkonlyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaultPkonlyService implements PkonlyService {

    @Autowired
    private PkonlyMapper pkonlyMapper;


    @Override
    public void addPkonly(Pkonly pkonly) {
        pkonlyMapper.insert(pkonly);
    }

    @Override
    public List<Pkonly> fetchPkonlys() {
        return pkonlyMapper.selectAll();
    }
}
