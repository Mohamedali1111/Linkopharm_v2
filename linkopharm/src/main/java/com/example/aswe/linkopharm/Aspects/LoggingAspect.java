package com.example.aswe.linkopharm.Aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component

public class LoggingAspect {

    @Pointcut("execution(* com.example.aswe.linkopharm.controllers.UserController.addUser() )")
public void logMessagePointCut(){}

    @Before("logMessagePointCut()")
    public void beforeAdvice(JoinPoint joinPoint) {
        System.out.println("AOP: Before Advice" + joinPoint.getSignature());
                System.out.println(joinPoint.getSignature()+"is called" );

    }

}