package com.clnn.hsqldb.service;

import com.clnn.hsqldb.entity.Pkonly;

import java.util.List;

public interface PkonlyService {

    void addPkonly(Pkonly pkonly);

    List<Pkonly> fetchPkonlys();
}
