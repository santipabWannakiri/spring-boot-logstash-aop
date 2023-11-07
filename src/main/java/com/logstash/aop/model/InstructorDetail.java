package com.logstash.aop.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "instructor_detail")
@Data
public class InstructorDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String youtube_channel;
    private String hobby;

    public InstructorDetail() {
    }

    public InstructorDetail(String youtube_channel, String hobby) {
        this.youtube_channel = youtube_channel;
        this.hobby = hobby;
    }
}
