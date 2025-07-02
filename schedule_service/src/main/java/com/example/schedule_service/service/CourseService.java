package com.example.schedule_service.service;

import java.util.Collections;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.schedule_service.entity.Course;
import com.example.schedule_service.repository.CourseRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CourseService {

    private final CourseRepository courseRepository;

    public List<Course> getCoursesByStudentIdSemesterCode(String studentId, String semesterCode) {
        var courses = 
        // courseRepository.findAll().stream()
        //         .filter(course -> course.getSemester().getSemesterCode().equals(semesterCode))
        //         .filter(t -> courseStudentRepository.findAll().stream()
        //                 .filter(cs -> cs.getStudent().getId().equals(studentId)
        //                         && cs.getCourse().getCourseId() == t.getCourseId())
        //                 .findFirst()
        //                 .isPresent())
        //         .collect(Collectors.toSet());
        // courseStudentRepository.findByCourse_Semester_semesterCodeAndStudentId(semesterCode, studentId).stream().map(courseStudentMapper::toCourseStudentResponse).toList()
        courseRepository.findBySemester_SemesterCodeAndCourseStudents_Student_Id(semesterCode, studentId)
        ;
        return courses;
    }

    public List<Course> findBySemesterCode(String semesterCode) {
        return courseRepository.findBySemester_SemesterCode(semesterCode);
    }

    public Course updateCourse(Course course) {
        return courseRepository.findById(course.getCourseId())
                .map(existingCourse -> {
                    if(course.getCourseStudents() != null && !course.getCourseStudents().isEmpty()) {
                        existingCourse.setCourseStudents(course.getCourseStudents());
                    }
                    existingCourse.setSemester(course.getSemester());
                    existingCourse.setSubject(course.getSubject());
                    existingCourse.setTeacher(course.getTeacher());
                    existingCourse.setCourseStudents(course.getCourseStudents());
                    return courseRepository.save(existingCourse);
                })
                .orElseThrow(() -> new IllegalArgumentException("Course not found"));
    }

    public void saveCourse(Course course) {
        courseRepository.save(course);
    }

    public void deleteById(Long id) {
        courseRepository.deleteById(id);
    }

    public Course findById(Long id) {
        return courseRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Course not found"));
    }

    private Pageable createPageRequest(int page, int size) {
        return PageRequest.of(page, size);
    }

    public Page<Course> getCourses(int page, int size) {
        List<Course> courses = courseRepository.findAll();

        Pageable pageRequest = createPageRequest(page, size);

        int start = (int) pageRequest.getOffset();
        int end = Math.min((start + pageRequest.getPageSize()), courses.size());

        List<Course> pageContent = courses.subList(start, end);
        return new PageImpl<>(pageContent, pageRequest, courses.size());
    }
    
    public List<Course> getCourseList() {
        return courseRepository.findAll();
    }
    
    public List<Course> getCourseListFromPage(int page, int size) {
        Pageable pageRequest = createPageRequest(page, size);
        Page<Course> courses = courseRepository.findAll(pageRequest);

        return courses.hasContent() ? courses.getContent() : Collections.emptyList();
    }
}
