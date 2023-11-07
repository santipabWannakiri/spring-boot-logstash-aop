package com.logstash.aop.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "instructor")
@Data
public class Instructor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String first_name;
    private String last_name;
    private String email;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "instructor_detail_id")
    private InstructorDetail instructorDetail;

    public Instructor() {
    }

    public Instructor(String first_name, String last_name, String email, InstructorDetail instructorDetail) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.instructorDetail = instructorDetail;
    }
}