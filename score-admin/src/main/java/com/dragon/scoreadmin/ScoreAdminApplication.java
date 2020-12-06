package com.dragon.scoreadmin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.dragon.*"})
@MapperScan(basePackages = {"com.dragon.scoreapi.repository"})
public class ScoreAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScoreAdminApplication.class, args);
    }

}
