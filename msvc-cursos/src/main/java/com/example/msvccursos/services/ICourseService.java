package com.example.msvccursos.services;

import com.example.msvccursos.entities.Course;

import java.util.List;
import java.util.Optional;

public interface ICourseService {

    List<Course> list();

    Optional<Course> findById(Long id);

    Course save(Course course);

    void delete(Course course);
}
