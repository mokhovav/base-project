package com.mokhovav.base.annotations;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class TrackingInjection {
    @Autowired
    Logger logger;

    public TrackingInjection() {
    }

    @Around("@annotation(Tracking)")
    public Object log(ProceedingJoinPoint joinPoint) throws Throwable {

        Object proceed = null;
        Throwable interceptor = null;
        Signature signature = joinPoint.getSignature();
        String name = signature.getDeclaringTypeName() + ".\033[34m" + signature.getName() + "\033[0m";
        String value = ((MethodSignature) signature).getMethod().getDeclaredAnnotation(Tracking.class).value();

        if (value.isEmpty()) logger.debug("\033[34mSTART:\033[0m " + name);
        else logger.debug("\033[34mSTART:\033[0m " + name + " Text: \033[33m" + value + "\033[0m");

        long start = System.currentTimeMillis();

        try {
            proceed = joinPoint.proceed();
        } catch (Throwable throwable) {
            interceptor = throwable;
        }
        long executionTime = System.currentTimeMillis() - start;
        logger.debug("\033[34mSTOP:  \033[0m" + name + " executed in \033[34m" + executionTime + "ms\033[0m");

        if (interceptor != null) throw interceptor;
        return proceed;
    }
}
