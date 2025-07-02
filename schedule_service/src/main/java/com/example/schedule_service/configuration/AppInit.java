package com.example.schedule_service.configuration;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.core.RedisTemplate;

import com.example.schedule_service.entity.Course;
import com.example.schedule_service.entity.Room;
import com.example.schedule_service.entity.Semester;
import com.example.schedule_service.entity.Slot;
import com.example.schedule_service.entity.Subject;
import com.example.schedule_service.entity.User;
import com.example.schedule_service.entity.dto.response.identity_service.UserCreationRequest;
import com.example.schedule_service.entity.dto.response.identity_service.UserResponse;
import com.example.schedule_service.repository.AttendanceRepository;
import com.example.schedule_service.repository.CourseRepository;
import com.example.schedule_service.repository.CourseStudentRepository;
import com.example.schedule_service.repository.RoomRepository;
import com.example.schedule_service.repository.SemesterRepository;
import com.example.schedule_service.repository.SlotRepository;
import com.example.schedule_service.repository.SubjectRepository;
import com.example.schedule_service.repository.UserRepository;
import com.example.schedule_service.repository.http_client.IdentityClient;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class AppInit {
    // private final ScheduleRepository scheduleRepository;

    private final SubjectRepository subjectRepository;

    private final SlotRepository slotRepository;

    private final RoomRepository roomRepository;

    /**
     * Initialization methods for application startup.
     */
    @Bean
    @Profile("!test")
    CommandLineRunner commandLineRunner(
            CourseRepository courseRepository,
            SemesterRepository semesterRepository,
            UserRepository userRepository,
            CourseStudentRepository courseStudentRepository,
            IdentityClient identityClient, 
            AttendanceRepository attendanceRepository,
            RedisTemplate<String, Object> redisTemplate) {
        return args -> {
            redisTemplate.opsForValue().set("demo", "tests", Duration.ofMinutes(1));
            
            if (subjectRepository.count() > 0 && userRepository.count() > 0) {
                return;
            }
            var subjects = List.of(
                    Subject.builder()
                            .subjectCode("SWP")
                            .subjectName("software project")
                            .build(),
                    Subject.builder()
                            .subjectCode("SWT")
                            .subjectName("software testing")
                            .build(),
                    Subject.builder()
                            .subjectCode("PRF")
                            .subjectName("programming")
                            .build(),
                    Subject.builder().subjectCode("PRJ").subjectName("java web").build());
            subjectRepository.saveAll(subjects);

            var slots = List.of(
                    Slot.builder()
                            .startTime(LocalTime.of(7, 30, 0))
                            .endTime(LocalTime.of(9, 0, 0))
                            .build(),
                    Slot.builder()
                            .startTime(LocalTime.of(9, 10, 0))
                            .endTime(LocalTime.of(10, 40, 0))
                            .build());
            slotRepository.saveAll(slots);

            var rooms = List.of(
                    Room.builder().roomNumber("AL-R301").build(),
                    Room.builder().roomNumber("DE-310").build(),
                    Room.builder().roomNumber("AL-L103").build());
            roomRepository.saveAll(rooms);

            var semesters = List.of(
                    Semester.builder()
                            .semesterCode("FA24")
                            .startDate(LocalDate.of(2024, 7, 1))
                            .endDate(LocalDate.of(2024, 12, 31))
                            .build(),
                    Semester.builder()
                            .semesterCode("SP24")
                            .startDate(LocalDate.of(2024, 1, 1))
                            .endDate(LocalDate.of(2024, 6, 30))
                            .build(),
                    Semester.builder()
                            .semesterCode("FA25")
                            .startDate(LocalDate.of(2025, 9, 1))
                            .endDate(LocalDate.of(2025, 12, 31))
                            .build(),
                    Semester.builder()
                            .semesterCode("SP25")
                            .startDate(LocalDate.of(2025, 1, 1))
                            .endDate(LocalDate.of(2025, 4, 30))
                            .build(),
                    Semester.builder()
                            .semesterCode("SU25")
                            .startDate(LocalDate.of(2025, 5, 1))
                            .endDate(LocalDate.of(2025, 8, 31))
                            .build()
                    );
            semesterRepository.saveAll(semesters);

            User teacher = null, student = null;

            if (userRepository.findByEmail("lamthon@gmail.com").isPresent()) {
                teacher = userRepository.findByEmail("lamthon@gmail.com").get();
                student = userRepository.findByEmail("teacher@gmail.com").get();
            } else {
                while (true) {
                    try {
                        teacher = User.builder()
                                .email("teacher@gmail")
                                .fullName("teacher")
                                .userId(identityClient
                                        .getUserByUsername("teacher1")
                                        .getId())
                                .build();
                        student = User.builder()
                                .userId(identityClient
                                        .getUserByUsername("student1")
                                        .getId())
                                .email("lamthon@gmail.com")
                                .fullName("Lam Thon")
                                .dob(LocalDate.of(2003, 7, 16))
                                .build();
                        userRepository.saveAll(List.of(student, teacher));
                        // return;

                        break;
                    } catch (Exception e) {
                        System.out.println("start identity_service di anh La^m Tho^n`" + e.getMessage());
                        try {
                            Thread.sleep(3000);

                        } catch (Exception e2) {
                            //
                        }
                    }
                }
            }
            // var users = List.of(teacher, student);
            
            for (int i = 0; i < 20; i++) {
                try {
                    if (identityClient.existsByUsername(("user" + i))) {
                        UserResponse user = identityClient.getUserByUsername("user" + i);
                        userRepository.save(User.builder()
                                .fullName("user" + i)
                                .email("email" + i + "@gmail.com")
                                .userId(user.getId())
                                .build());
                        continue;
                    }
                    identityClient.createUser(UserCreationRequest.builder()
                            .username("user" + i)
                            .password("user" + i)
                            .fullName("user" + i)
                            .email("email" + i + "@gmail.com")
                            .build());

                } catch (Exception e) {
                    // UserResponse user = identityClient.getUserByUsername("user" + i);
                    // userRepository.save(User.builder()
                    // .fullName("user" + i)
                    // .email("email" + i + "@gmail.com")
                    // .userId(user.getId())
                    // .build());
                }
            }

            // student.setCourseStudents(Set.of(CourseStudent.builder()
            // .course(courses.get(0))
            // .student(student)
            // .build()));
            var fall25 = semesters.stream()
                    .filter(s -> s.getSemesterCode().equals("FA25"))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Fall 2025 semester not found"));
            var courses = List.of(
                    Course.builder()
                            .subject(subjects.get(0))
                            .semester(fall25)
                            .teacher(teacher)
                            .build(),
                    Course.builder()
                            .subject(subjects.get(1))
                            .semester(fall25)
                            .teacher(teacher)
                            .build(),
                    Course.builder()
                            .subject(subjects.get(2))
                            .semester(fall25)
                            .teacher(teacher)
                            .build(),
                    Course.builder()
                            .subject(subjects.get(3))
                            .semester(fall25)
                            .teacher(teacher)
                            .build());
            courseRepository.saveAll(courses);

            // var courseStudents = List.of(
            // CourseStudent.builder()
            // .course(courses.get(0))
            // .student(student)
            // .id(CourseStudentKey.builder()
            // .courseId(courses.get(0).getCourseId())
            // .studentId(users.get(1).getId())
            // .build())
            // .build(),
            // CourseStudent.builder()
            // .student(student)
            // .course(courses.get(1))
            // .id(CourseStudentKey.builder()
            // .courseId(courses.get(1).getCourseId())
            // .studentId(users.get(1).getId())
            // .build())
            // .build());

            // courseStudentRepository.saveAll(courseStudents);
            // // student.setCourseStudents(courseStudents);

            // var schedules = List.of(
            // Schedule.builder()
            // .subject(subjects.get(0))
            // .room(rooms.get(0))
            // .slot(slots.get(0))
            // .trainingDate(LocalDate.of(2024, 7, 10))
            // .course(courses.get(0))
            // .build(),
            // Schedule.builder()
            // .subject(subjects.get(1))
            // .room(rooms.get(0))
            // .slot(slots.get(0))
            // .trainingDate(LocalDate.of(2024, 7, 9))
            // .course(courses.get(1))
            // .build());
            // scheduleRepository.saveAll(schedules);

            // var attendances = schedules.stream()
            // .map(s -> Attendance.builder()
            // .schedule(s)
            // .student(users.get(1))
            // .build())
            // .toList();
            // attendanceRepository.saveAll(attendances);
        };
    }
}
