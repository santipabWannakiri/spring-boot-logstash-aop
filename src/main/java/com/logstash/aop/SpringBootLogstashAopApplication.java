package com.logstash.aop;

import com.logstash.aop.model.Instructor;
import com.logstash.aop.model.InstructorDetail;
import com.logstash.aop.service.InstructorService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringBootLogstashAopApplication {

    private InstructorService instructorService;

    public SpringBootLogstashAopApplication(InstructorService instructorService) {
        this.instructorService = instructorService;
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringBootLogstashAopApplication.class, args);
    }

    @Bean
    public CommandLineRunner initialInstructor(String[] args) {
        return r -> {
            createInstructorObject();
        };
    }

    private void createInstructorObject() {
        InstructorDetail instructorDetailA = new InstructorDetail("https://youtube.com/somsak.s", "Cycling");
        Instructor instructorA = new Instructor("SOMSAK", "SANDEE", "SOMSAK.S@gmail.com", instructorDetailA);

        InstructorDetail instructorDetailB = new InstructorDetail("https://youtube.com/somchai", "Singing");
        Instructor instructorB = new Instructor("SOMCHAI", "RIT", "SOMCHAI.R@gmail.com", instructorDetailB);

        InstructorDetail instructorDetailC = new InstructorDetail("https://youtube.com/jirapon", "Tiktoker");
        Instructor instructorC = new Instructor("JIRAPON", "KIM", "JIRAPON.K@gmail.com", instructorDetailC);

        instructorService.save(instructorA);
        instructorService.save(instructorB);
        instructorService.save(instructorC);
    }

}
