package by.pilipuk.service;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import java.util.Arrays;

@Aspect
@Component
@Slf4j
public class RepositoryExceptionLoggingService {
    @Before("execution(* by.pilipuk.repository.*Repository.find*(..))")
    public void logRepositoryMethods(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getSignature().getDeclaringTypeName();
        Object[] args = joinPoint.getArgs();

        log.info("[Repository] Execution started: {}.{}({})",
                className,
                methodName,
                Arrays.toString(args));
    }
}