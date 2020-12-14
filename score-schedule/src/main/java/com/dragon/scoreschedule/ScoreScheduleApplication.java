package com.dragon.scoreschedule;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.dragon.*"})
@MapperScan(basePackages = {"com.dragon.scoreapi.repository"})
public class ScoreScheduleApplication {

    public static void main(String[] args) {
        float r = 1.5f;
        for(float y = (float) 1.5;y>-1.5;y -=0.1)  {
            for(float x= (float) -1.5;x<1.5;x+= 0.05){
                float a = x*x+y*y-1;
                if((a*a*a-x*x*y*y*y)<=0.0)  {
                    System.out.print("^");
                }
                else
                    System.out.print(" ");
            }
            System.out.println();
        }
        SpringApplication.run(ScoreScheduleApplication.class, args);
    }

}
