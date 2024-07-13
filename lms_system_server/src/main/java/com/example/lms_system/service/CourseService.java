package com.example.lms_system.service;

import java.util.Collections;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.lms_system.entity.Course;
import com.example.lms_system.repository.CourseRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CourseService {

    private final CourseRepository courseRepository;

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
