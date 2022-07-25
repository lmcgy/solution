package com.liu.es;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * @Author: Gavin
 * @Date: 2021/12/20 14:58
 */
@MapperScan("com.liu.es.mapper")
@SpringBootApplication
public class SolutionEsApplication {
    public static void main(String[] args) {
        SpringApplication.run(SolutionEsApplication.class, args);
    }

    @Bean
    public RestHighLevelClient client(){
        return new RestHighLevelClient(RestClient.builder(HttpHost.create("http://192.168.56.101:9200")));
    }
}
