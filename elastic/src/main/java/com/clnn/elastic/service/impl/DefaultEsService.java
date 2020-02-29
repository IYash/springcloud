package com.clnn.elastic.service.impl;

import com.clnn.elastic.service.EsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Service;

@Service
public class DefaultEsService implements EsService {

    @Autowired
    ElasticsearchTemplate esTemplate;

    @Override
    public void createIndex(Class clz) {
        esTemplate.createIndex(clz);//创建索引
        esTemplate.putMapping(clz);//配置映射
    }

    @Override
    public void createIndexByName(String indexName) {
        esTemplate.createIndex(indexName);
    }

    @Override
    public void deleteIndex(Class clz) {
        esTemplate.deleteIndex(clz);
    }

    @Override
    public void deleteIndexByName(String indexName) {
        esTemplate.deleteIndex(indexName);
    }
}
