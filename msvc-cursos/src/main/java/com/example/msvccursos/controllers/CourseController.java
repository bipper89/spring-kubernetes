package com.example.msvccursos.controllers;

import com.example.msvccursos.entities.Course;
import com.example.msvccursos.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    @Autowired
    private CourseService service;

    @GetMapping
    public ResponseEntity<?> index() {
        return ResponseEntity.ok().body(this.service.list());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> show(@PathVariable Long id) {
        Optional<Course> o = this.service.findById(id);
        if (o.isPresent()) {
            return ResponseEntity.ok().body(o.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/")
    public ResponseEntity<?> create(@Valid @RequestBody Course course, BindingResult result) {
        if (result.hasErrors()) {
            return validate(result);
        }
        return ResponseEntity.status(CREATED).body(this.service.save(course));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody Course course, BindingResult result, @PathVariable Long id) {
        if (result.hasErrors()) {
            return validate(result);
        }
        Optional<Course> o = this.service.findById(id);
        if (o.isPresent()) {
            Course currentCourse = o.get();
            currentCourse.setName(course.getName());
            return ResponseEntity.status(CREATED).body(this.service.save(currentCourse));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Optional<Course> o = this.service.findById(id);
        if (o.isPresent()) {
            this.service.delete(o.get());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    private static ResponseEntity<Map<String, String>> validate(BindingResult result) {
        Map<String, String> errors = new HashMap<>();
        result.getFieldErrors()
                .forEach(error -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });
        return ResponseEntity.status(BAD_REQUEST).body(errors);
    }

}
