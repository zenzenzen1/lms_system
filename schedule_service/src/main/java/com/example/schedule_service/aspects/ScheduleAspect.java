package com.example.schedule_service.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.example.schedule_service.entity.dto.request.ScheduleRequest;
import com.example.schedule_service.exception.AppException;
import com.example.schedule_service.exception.ErrorCode;
import com.example.schedule_service.repository.CourseRepository;
import com.example.schedule_service.repository.ScheduleRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class ScheduleAspect {
    @SuppressWarnings("unused")
    private final KafkaTemplate<String, Object> kafkaTemplate;

    private final ScheduleRepository scheduleRepository;
    private final CourseRepository courseRepository;

    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    public void controllerMethods() {}

    @Before("execution(* com.example.lms_system.controller.ScheduleController.addSchedules(..))")
    public void doBeforeSaveSchedules(ScheduleRequest scheduleRequest) {
        log.info("do before save schedule with schedule request {}", scheduleRequest);
    }

    @Around(
            "execution(* com.example.lms_system.controller.ScheduleController.addSchedules(com.example.lms_system.dto.request.ScheduleRequest)) && args(scheduleRequest, ..)")
    public Object doAroundSaveSchedules(ProceedingJoinPoint joinPoint, ScheduleRequest scheduleRequest)
            throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        String remoteAddress = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getRequest()
                .getRemoteAddr();
        log.info("Around function: {} from {}", methodName, remoteAddress);
        // for (Object iterable_element : joinPoint.getArgs()) {
        //     log.info("Argument: {}", iterable_element);
        // }
        var existsBySemesterTeacherIdRoomId = scheduleRepository.existsBySemesterTeacherIdRoomId(
                scheduleRequest.getSlotId(),
                scheduleRequest.getSemesterCode(),
                courseRepository
                        .findById(scheduleRequest.getCourseId())
                        .get()
                        .getTeacher()
                        .getId());
        log.info("schedule requesst: {}, isValid: {}", scheduleRequest, existsBySemesterTeacherIdRoomId);
        if (existsBySemesterTeacherIdRoomId) {
            throw new AppException(ErrorCode.ADD_SCHEDULE_ERROR);
        }
        return joinPoint.proceed();
    }

    @After("execution(* com.example.lms_system.controller.ScheduleController.addSchedules(..))")
    public void doAfterSaveSchedules(ProceedingJoinPoint joinPoint) {

        log.info("After executing addSchedules function");
    }
}
