package com.example.msvccursos.services;

import com.example.msvccursos.entities.Course;
import com.example.msvccursos.repositories.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService implements ICourseService {

    @Autowired
    private CourseRepository repository;

    @Override
    public List<Course> list() {
        return this.repository.findAll();
    }

    @Override
    public Optional<Course> findById(Long id) {
        return this.repository.findById(id);
    }

    @Override
    public Course save(Course course) {
        return this.repository.save(course);
    }

    @Override
    public void delete(Course course) {
        this.repository.delete(course);
    }
}
