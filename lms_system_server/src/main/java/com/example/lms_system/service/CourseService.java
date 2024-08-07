package com.example.lms_system.service;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.lms_system.entity.Course;
import com.example.lms_system.repository.CourseRepository;
import com.example.lms_system.repository.CourseStudentRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CourseService {

    private final CourseRepository courseRepository;
    private final CourseStudentRepository courseStudentRepository;

    public Set<Course> getCoursesByStudentIdSemesterCode(String studentId, String semesterCode) {
        var courses = courseRepository.findAll().stream()
                .filter(course -> course.getSemester().getSemesterCode().equals(semesterCode))
                .filter(t -> courseStudentRepository.findAll().stream()
                        .filter(cs -> cs.getStudent().getId().equals(studentId)
                                && cs.getCourse().getCourseId() == t.getCourseId())
                        .findFirst()
                        .isPresent())
                .collect(Collectors.toSet());
        return courses;
    }

    public Set<Course> findBySemesterCode(String semesterCode) {
        return courseRepository.findAll().stream()
                .filter(t -> t.getSemester().getSemesterCode().equals(semesterCode))
                .collect(Collectors.toSet());
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

    public List<Course> getCourseListFromPage(int page, int size) {
        Pageable pageRequest = createPageRequest(page, size);
        Page<Course> courses = courseRepository.findAll(pageRequest);

        return courses.hasContent() ? courses.getContent() : Collections.emptyList();
    }
}
