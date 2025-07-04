package com.example.schedule_service.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.schedule_service.entity.Course;
import com.example.schedule_service.entity.Semester;
import com.example.schedule_service.entity.Subject;
import com.example.schedule_service.entity.User;
import com.example.schedule_service.entity.dto.response.ApiResponse;
import com.example.schedule_service.service.CourseService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = {"/courses", "/courses/"})
public class CourseController {

    private final CourseService courseService;

    @GetMapping("/semesterCode/{semesterCode}")
    public Object findBySemesterCode(@PathVariable String semesterCode) {
        return courseService.findBySemesterCode(semesterCode);
    }

    @GetMapping("/studentId/semesterCode")
    public ResponseEntity<Object> getCoursesByStudentIdSemesterCode(@RequestParam String studentId, @RequestParam String semesterCode) {
        return ResponseEntity.ok(ApiResponse.<Object>builder()
                .httpStatus(HttpStatus.OK)
                .message("Courses retrieved successfully")
                .result(courseService.getCoursesByStudentIdSemesterCode(studentId, semesterCode))
                .build());
    }

    @GetMapping("/all")
    public ResponseEntity<Page<Course>> getAttendances(
            @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        Page<Course> coursePage = courseService.getCourses(page, size);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Course-page-number", String.valueOf(coursePage.getNumber()));
        headers.add("Course-page-size", String.valueOf(coursePage.getSize()));
        return ResponseEntity.ok().headers(headers).body(coursePage);
    }

    @GetMapping("/details")
    public ResponseEntity<Course> getCourseDetails(@RequestParam Long id) {
        return ResponseEntity.ok(courseService.findById(id));
    }

    @PostMapping("/add")
    public ResponseEntity<Course> addCourse(
            @RequestParam Subject subject, @RequestParam Semester semester, @RequestParam User teacher) {
        Course course = Course.builder()
                .subject(subject)
                .semester(semester)
                .teacher(teacher)
                .build();
        courseService.saveCourse(course);
        return new ResponseEntity<>(course, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Course> updateCourse(
            @PathVariable Long id,
            @RequestParam Subject subject,
            @RequestParam Semester semester,
            @RequestParam User teacher) {
        Course course = courseService.findById(id);
        if (course == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        course.setSubject(subject);
        course.setSemester(semester);
        course.setTeacher(teacher);
        courseService.saveCourse(course);
        return new ResponseEntity<>(course, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
        Course course = courseService.findById(id);
        if (course == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        courseService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
