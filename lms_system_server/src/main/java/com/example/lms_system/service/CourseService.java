package com.example.lms_system.service;

import java.util.Collections;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.lms_system.dto.request.CourseRequest;
import com.example.lms_system.dto.response.CourseResponse;
import com.example.lms_system.entity.Course;
import com.example.lms_system.mapper.CourseMapper;
import com.example.lms_system.repository.CourseRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CourseService {

    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;

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

    public CourseResponse insertCourse(CourseRequest request) {
        var course = courseMapper.toCourse(request);
        return courseMapper.toCourseResponse(courseRepository.save(course));
    }

    public CourseResponse updateCourse(CourseRequest request) {
        var target = courseMapper.toCourse(request);
        var course = courseRepository.findById(target.getCourseId())
                                    .orElseThrow(() -> new IllegalArgumentException("Course not found"));
        course = courseMapper.toCourse(request);
        return courseMapper.toCourseResponse(courseRepository.save(course));                            
    }
}
