package com.dragon.scoreapp.aspect;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class LoginAspect {

    @Pointcut("execution(public * com.dragon.scoreapp.security.JwtLoginFilter.successfulAuthentication())")
    public void loginLog() {

    }

    @Before("loginLog()")
    public void doBefore(JoinPoint joinPoint) {
        log.info("切入。。。。。。。");
    }
}
