package com.example.lms_system.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import com.example.lms_system.dto.request.AttendanceRequest;

import lombok.extern.slf4j.Slf4j;

@Component
@Aspect
@Slf4j
public class AttendanceAspect {
    @Around("execution(* com.example.lms_system.controller.AttendanceController.saveAllAttendance(..))")
    public Object doAroundSaveAttendances(ProceedingJoinPoint joinPoint) throws Throwable {
        AttendanceRequest attendanceRequest = (AttendanceRequest) joinPoint.getArgs()[0];
        log.info("Around function: saveAllAttendance from {}", attendanceRequest);

        return joinPoint.proceed();
    }
}
