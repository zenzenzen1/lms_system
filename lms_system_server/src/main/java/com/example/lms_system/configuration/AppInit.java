package com.example.lms_system.configuration;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.lms_system.entity.Course;
import com.example.lms_system.entity.CourseStudent;
import com.example.lms_system.entity.Room;
import com.example.lms_system.entity.Schedule;
import com.example.lms_system.entity.Semester;
import com.example.lms_system.entity.Slot;
import com.example.lms_system.entity.Subject;
import com.example.lms_system.entity.User;
import com.example.lms_system.entity.key.CourseStudentKey;
import com.example.lms_system.repository.CourseRepository;
import com.example.lms_system.repository.CourseStudentRepository;
import com.example.lms_system.repository.RoomRepository;
import com.example.lms_system.repository.ScheduleRepository;
import com.example.lms_system.repository.SemesterRepository;
import com.example.lms_system.repository.SlotRepository;
import com.example.lms_system.repository.SubjectRepository;
import com.example.lms_system.repository.UserRepository;
import com.example.lms_system.repository.http_client.IdentityClient;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class AppInit {
    private final ScheduleRepository scheduleRepository;

    private final SubjectRepository subjectRepository;

    private final SlotRepository slotRepository;

    private final RoomRepository roomRepository;

    @Bean
    CommandLineRunner commandLineRunner(
            CourseRepository courseRepository,
            SemesterRepository semesterRepository,
            UserRepository userRepository,
            CourseStudentRepository courseStudentRepository,
            IdentityClient identityClient) {
        return args -> {
            var subjects = List.of(
                    Subject.builder()
                            .subjectCode("SWP")
                            .subjectName("software project")
                            .build(),
                    Subject.builder()
                            .subjectCode("SWT")
                            .subjectName("software testing")
                            .build());
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
                            .semesterCode("FA2024")
                            .startDate(LocalDate.of(2024, 7, 1))
                            .endDate(LocalDate.of(2024, 12, 31))
                            .build(),
                    Semester.builder()
                            .semesterCode("SP2024")
                            .startDate(LocalDate.of(2024, 1, 1))
                            .endDate(LocalDate.of(2024, 6, 30))
                            .build());
            semesterRepository.saveAll(semesters);

            User teacher, student;
            if (userRepository.findByEmail("lamthon@gmail.com").isPresent()) {
                teacher = userRepository.findByEmail("lamthon@gmail.com").get();
                student = userRepository.findByEmail("teacher@gmail.com").get();
            } else {

                teacher = User.builder()
                        .email("teacher@gmail")
                        .fullName("teacher")
                        .userId(identityClient.getUserByUsername("teacher1").getId())
                        .build();
                student = User.builder()
                        .userId(identityClient.getUserByUsername("student1").getId())
                        .email("lamthon@gmail.com")
                        .fullName("Lam Thon")
                        .dob(LocalDate.of(2003, 7, 16))
                        .build();
                userRepository.saveAll(List.of(student, teacher));
                // return;
            }
            // student.setCourseStudents(Set.of(CourseStudent.builder()
            // .course(courses.get(0))
            // .student(student)
            // .build()));
            // userRepository.saveAll(List.of(teacher, student));
            var courses = List.of(
                    Course.builder()
                            .subject(subjects.get(0))
                            .semester(semesters.get(0))
                            .teacher(teacher)
                            .build(),
                    Course.builder()
                            .subject(subjects.get(1))
                            .semester(semesters.get(1))
                            .teacher(teacher)
                            .build());
            courseRepository.saveAll(courses);

            var courseStudents = List.of(
                    CourseStudent.builder()
                            .course(courses.get(0))
                            .student(student)
                            .id(CourseStudentKey.builder()
                                    .courseId(courses.get(0).getCourseId())
                                    .studentId(student.getId())
                                    .build())
                            .build(),
                    CourseStudent.builder()
                            .student(student)
                            .course(courses.get(1))
                            .id(CourseStudentKey.builder()
                                    .courseId(courses.get(1).getCourseId())
                                    .studentId(student.getId())
                                    .build())
                            .build());

            courseStudentRepository.saveAll(courseStudents);
            // student.setCourseStudents(courseStudents);

            scheduleRepository.saveAll(List.of(
                    Schedule.builder()
                            .subject(subjects.get(0))
                            .room(rooms.get(0))
                            .slot(slots.get(0))
                            .trainingDate(LocalDate.of(2024, 7, 10))
                            .course(courses.get(0))
                            .build(),
                    Schedule.builder()
                            .subject(subjects.get(1))
                            .room(rooms.get(0))
                            .slot(slots.get(0))
                            .trainingDate(LocalDate.of(2024, 7, 9))
                            .course(courses.get(1))
                            .build()));
        };
    }
}
