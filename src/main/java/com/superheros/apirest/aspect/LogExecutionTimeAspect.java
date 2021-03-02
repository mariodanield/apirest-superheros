package com.superheros.apirest.aspect;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import com.superheros.apirest.annotation.LogExecutionTime;
import java.lang.reflect.Method;

@Aspect
@Component
public class LogExecutionTimeAspect {

	private static final Logger logger = LogManager.getLogger(LogExecutionTimeAspect.class);
	
    @Around("@annotation(com.superheros.apirest.annotation.LogExecutionTime)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        LogExecutionTime myAnnotation = method.getAnnotation(LogExecutionTime.class);

        long start = System.currentTimeMillis();

        Object proceed = joinPoint.proceed();

        long executionTime = System.currentTimeMillis() - start;

        logger.info("{} | {} executed in {} ms",myAnnotation.name(), joinPoint.getSignature() ,executionTime);
        return proceed;
    }
}