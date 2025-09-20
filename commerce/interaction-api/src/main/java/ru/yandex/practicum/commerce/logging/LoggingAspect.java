package ru.yandex.practicum.commerce.logging;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger log = LoggerFactory.getLogger(LoggingAspect.class);

    public LoggingAspect() {
        log.info("Aspect поднят!");
    }


    @Around("@annotation(Logging)")
    public Object logMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String methodName = signature.getDeclaringType().getSimpleName() + "." + signature.getName();

        Object[] args = joinPoint.getArgs();
        log.info("Вход в метод {} с аргументами: {}", methodName, args);

        Object result;
        try {
            result = joinPoint.proceed();
        } catch (Throwable throwable) {
            log.error("Метод {} выбросил исключение: {}", methodName, throwable.getMessage());
            throw throwable;
        }

        log.info("Выход из метода {} с результатом: {}", methodName, result);
        return result;
    }
}