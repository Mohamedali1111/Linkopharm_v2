package com.example.aswe.linkopharm.Aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    @Pointcut("execution(* com.example.aswe.linkopharm.controllers.*.*(..))")
    public void controllerPointcut() {}

    @Pointcut("execution(* com.example.aswe.linkopharm.repositories.*.*(..))")
    public void repositoryPointcut() {}

    @Before("controllerPointcut() || repositoryPointcut()")
    public void beforeMethods(JoinPoint joinPoint) {
        System.out.println("Before executing: " + joinPoint.getSignature());
    }

    @After("controllerPointcut() || repositoryPointcut()")
    public void afterMethods(JoinPoint joinPoint) {
        System.out.println("After executing: " + joinPoint.getSignature());
    }

    @AfterReturning(pointcut = "controllerPointcut() || repositoryPointcut()", returning = "result")
    public void afterReturning(JoinPoint joinPoint, Object result) {
        System.out.println("After returning from: " + joinPoint.getSignature() + ", result = " + result);
    }

    @AfterThrowing(pointcut = "controllerPointcut() || repositoryPointcut()", throwing = "exception")
    public void afterThrowing(JoinPoint joinPoint, Exception exception) {
        System.out.println("Exception thrown from: " + joinPoint.getSignature() + ", exception = " + exception.getMessage());
    }

    @Around("controllerPointcut() || repositoryPointcut()")
    public Object aroundMethods(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;
        System.out.println(joinPoint.getSignature() + " executed in " + executionTime + "ms");
        return result;
    }
}
