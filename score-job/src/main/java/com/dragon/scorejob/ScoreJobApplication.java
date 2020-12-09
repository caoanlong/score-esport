package com.dragon.scorejob;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@ComponentScan(basePackages = {"com.dragon.*"})
@MapperScan(basePackages = {"com.dragon.scoreapi.repository"})
public class ScoreJobApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScoreJobApplication.class, args);
    }

}
