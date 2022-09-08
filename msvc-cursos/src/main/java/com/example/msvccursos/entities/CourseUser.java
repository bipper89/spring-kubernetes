package com.example.msvccursos.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "courses_users")
@Getter
@Setter
public class CourseUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", unique = true)
    private Long userId;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof CourseUser)) {
            return false;
        }
        CourseUser o = (CourseUser) obj;
        return this.userId != null && this.userId.equals(((CourseUser) obj).userId);
    }
}
