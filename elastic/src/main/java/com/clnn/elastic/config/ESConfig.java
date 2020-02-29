//package com.clnn.elastic.config;
//
//import org.apache.http.HttpHost;
//import org.elasticsearch.client.RestClient;
//import org.elasticsearch.client.RestHighLevelClient;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.SpringBootConfiguration;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//
//@SpringBootConfiguration
//@ConfigurationProperties(prefix = "es.config")
//public class ESConfig {
//
//    @Value("${es.config.host}")
//    private String host;
//    @Value("${es.config.port}")
//    private int port;
//    @Value("${es.config.schema}")
//    private String schema;
//
//    @Bean
//    public RestHighLevelClient restHighLevelClient(){
//        return new RestHighLevelClient(RestClient.builder(new HttpHost(host,port,schema)));
//    }
//}
