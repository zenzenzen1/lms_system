package com.example.lms_system.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class ScheduleAspect {
    @SuppressWarnings("unused")
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    public void controllerMethods() {}

    @Around("execution(* com.example.lms_system.controller.ScheduleController.addSchedules(..))")
    public Object doAfterSaveSchedules(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        String remoteAddress = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getRequest()
                .getRemoteAddr();
        log.info("Around function: {} from {}", methodName, remoteAddress);
        for (Object iterable_element : joinPoint.getArgs()) {
            log.info("Argument: {}", iterable_element);
        }
        return joinPoint.proceed();
    }

    @After("execution(* com.example.lms_system.controller.ScheduleController.addSchedules(..))")
    public void doAfterSaveSchedules() {

        log.info("After executing addSchedules function");
    }
}
