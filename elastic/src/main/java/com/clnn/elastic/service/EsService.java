package com.clnn.elastic.service;

public interface EsService {
    void createIndex(Class clz);
    void createIndexByName(String indexName);

    void deleteIndex(Class clz);

    void deleteIndexByName(String indexName);
}
