package com.example.msvccursos.entities;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "courses")
@Getter
@Setter
@AllArgsConstructor
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CourseUser> courseUsers;

    public Course() {
        this.courseUsers = new ArrayList<>();
    }

    public void addCourseUser(CourseUser courseUser) {
        this.courseUsers.add(courseUser);
    }

    public void removeCourseUser(CourseUser courseUser) {
        this.courseUsers.remove(courseUser);
    }
}
