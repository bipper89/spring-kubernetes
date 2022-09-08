package com.example.msvccursos.repositories;

import com.example.msvccursos.entities.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
}
