package com.example.lms_system.aspects;

import java.util.Set;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.example.event.AttendanceStatusNotification;
import com.example.lms_system.dto.request.AttendanceRequest;
import com.example.lms_system.entity.Attendance;
import com.example.lms_system.service.AttendanceService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Aspect
@Slf4j
@RequiredArgsConstructor
public class AttendanceAspect {
    @SuppressWarnings("unused")
    private final KafkaTemplate<String, Object> kafkaTemplate;

    private final RedisTemplate<String, Object> redisTemplate;

    private final AttendanceService attendanceService;

    @Around("execution(* com.example.lms_system.controller.AttendanceController.saveAllAttendance(..) ) ")
    public Object doAroundSaveAttendances(ProceedingJoinPoint joinPoint) throws Throwable {
        AttendanceRequest attendanceRequest = (AttendanceRequest) joinPoint.getArgs()[0];
        // kafkaTemplate.send("send-eamil")
        log.info(
                "Around saveAttendances function: saveAllAttendance from {}",
                attendanceRequest.getAttendanceRequests());

        return joinPoint.proceed();
    }

    @AfterReturning(
            pointcut = "execution(* com.example.lms_system.controller.AttendanceController.saveAllAttendance(..))",
            returning = "result")
    public void doAfterReturningSaveAttendances(Set<Attendance> result) {
        // log.info("AfterReturning saveAttendances function: saveAllAttendance result: {}", result);
        result.forEach(t -> {
            redisTemplate.delete(redisTemplate.keys("schedule" + t.getStudent().getId() + "*"));
            double absentPercentage = attendanceService.getAbsentPercentage(
                    t.getStudent().getId(), t.getSchedule().getCourse().getCourseId());
            if (absentPercentage > 10) {

                log.info(
                        "Student {} has more than 10% absent in course {}",
                        t.getStudent().getId(), t.getSchedule().getCourse().getCourseId());
                kafkaTemplate.send(
                        "send-email-attendance-status",
                        AttendanceStatusNotification.builder()
                                .studentId(t.getStudent().getId())
                                .absentPercentage(absentPercentage)
                                .email(t.getStudent().getEmail())
                                .fullName(t.getStudent().getFullName())
                                .build());
            }
        });
    }
}
