package com.logstash.aop.configuration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.logstash.aop.model.LogInfo;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    //"execution(* com.centralized.logging.aop.controller.*.*(..)) || execution(* com.centralized.logging.aop.serviceImplCentralizeLog.*.*(..))"
    @Before("execution(* com.logstash.aop.serviceImp.*.*(..))")
    public void logMethodEntry(JoinPoint joinPoint) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        Object[] request = joinPoint.getArgs();
        log.info(mapper.writeValueAsString(new LogInfo(className, methodName, request)));
//        log.info("{}.{} : Response payload - {}", className, methodName, mapper.writeValueAsString(request));
    }

    @Around("execution(* com.logstash.aop.serviceImp.*.*(..))")
    public Object logMethodExitAround(ProceedingJoinPoint joinPoint) throws Throwable {
        ObjectMapper mapper = new ObjectMapper();
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        // Proceed with the method execution
        Object response = joinPoint.proceed();

        // Check the type of the response and handle accordingly
        if (response instanceof Optional<?>) {
            Optional<?> optionalResponse = (Optional<?>) response;

            if (optionalResponse.isPresent()) {
                // Response is present, handle it
                Object actualResponse = optionalResponse.get();
                log.info(mapper.writeValueAsString(new LogInfo(className, methodName, actualResponse)));
//                log.info("{}.{} : Response payload - {}", className, methodName, mapper.writeValueAsString(actualResponse));
            } else {
                // Response is empty (Optional is empty)
                log.info("{}.{} : Response is empty", className, methodName);
            }
        } else if (response instanceof List<?>) {
            // Response is a List, handle it
            List<?> listResponse = (List<?>) response;
            log.info(mapper.writeValueAsString(new LogInfo(className, methodName, listResponse)));
//            log.info("{}.{} : Response payload - {}", className, methodName, mapper.writeValueAsString(listResponse));
        } else {
            // Handle other types if needed
            log.info(mapper.writeValueAsString(new LogInfo(className, methodName, response)));
//            log.info("{}.{} : Response payload - {}", className, methodName, mapper.writeValueAsString(response));
        }
        return response;
    }
}
