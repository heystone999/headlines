package com.stone.es;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.stone.es.mapper")
public class EsInitApplication {
    public static void main(String[] args) {
        SpringApplication.run(EsInitApplication.class, args);
    }
}
