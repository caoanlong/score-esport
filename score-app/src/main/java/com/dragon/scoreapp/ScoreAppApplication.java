package com.dragon.scoreapp;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.dragon.*"})
@MapperScan(basePackages = {"com.dragon.scoreapi.repository"})
public class ScoreAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(ScoreAppApplication.class, args);
	}

}
